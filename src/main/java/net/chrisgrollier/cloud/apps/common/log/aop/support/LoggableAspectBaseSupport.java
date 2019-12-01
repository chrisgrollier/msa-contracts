package net.chrisgrollier.cloud.apps.common.log.aop.support;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.chrisgrollier.cloud.apps.common.log.LogData;
import net.chrisgrollier.cloud.apps.common.log.LogData.LogDataBuilder;
import net.chrisgrollier.cloud.apps.common.log.LogUtil;
import net.chrisgrollier.cloud.apps.common.log.aop.Loggable;
import net.chrisgrollier.cloud.apps.common.util.mapping.aop.AbstractAspect;

/**
 * Base support class for aspect working around methods annotated by
 * {@link Loggable} or methods where target class is annotated by
 * {@link Loggable}. Subclasses should only annotated its class with
 * {@link Aspect} and then implement at least one method annotated with
 * {@link Around} which simply calls and return result from
 * {@link #simpleAroundStrategy(ProceedingJoinPoint, Loggable)}
 * 
 * @author Atos
 */
public class LoggableAspectBaseSupport extends AbstractAspect<Loggable, LoggableAspectBaseSupport.AroundContext> {

	/**
	 * Build a new instance.
	 */
	public LoggableAspectBaseSupport() {
		super();
	}

	@Pointcut("within(net.chrisgrollier..*) && execution(public * *(..))")
	public void isWithinNetChrisGrollierPublicExecution() {
		/**/}

	@Pointcut("within(net.chrisgrollier..*) && execution(* *(..))")
	public void isWithinNetChrisGrollierAnyExecution() {
		/**/}

	@Pointcut("execution(* net.chrisgrollier..*(..))")
	public void isNetChrisGrollierAnyExecution() {
		/**/}

	/**
	 * Pointcut for {@link Loggable} annotation on types (classes) and methods
	 * 
	 * @param loggable the annotation instance to process
	 */
	@Pointcut("@target(loggable) || @annotation(loggable)")
	public void isLoggable(Loggable loggable) {
		/**/}

	@Override
	protected AroundContext doBefore(ProceedingJoinPoint pjp, Loggable context) {
		// if the @Loggable is at class level, context will be null
		// so to find Loggable attributes we need the class level value
		Loggable classLevelContext = this.getClassLevelLoggable(pjp);
		// get signature type
		final int signatureType = this.getLoggableSignatureType(context, classLevelContext);
		// get signature
		final String signature = this.getMethodSignature(pjp, signatureType);
		// get service
		final String service = this.getLoggableService(context, classLevelContext);
		// define current service
		LogDataBuilder builder = LogData.currentBuilder().service(service);
		// should we perf
		boolean perf = this.getLoggablePerf(classLevelContext, classLevelContext);
		// should we debug
		boolean debug = this.getLoggableDebug(classLevelContext, classLevelContext);
		// should we see args detail
		boolean showArgValues = this.getLoggableShowArgValues(context, classLevelContext);
		// get the log service
		final Logger logService = this.getLogService(pjp);
		// debug before
		if (debug) {
			LogUtil.debug(logService,
					builder.message("{} called with args ({})", signature, this.argsToString(pjp, showArgValues)));
		}
		return new AroundContext(builder, logService, signature, debug, perf, showArgValues);
	}

	@Override
	protected void doAfterReturning(ProceedingJoinPoint pjp, Loggable context, AroundContext aroundContext,
			Object result, long startTime, long endTime) {
		// debug after
		if (aroundContext.logService.isInfoEnabled() && aroundContext.perf) {
			if (aroundContext.debug) {
				LogUtil.performanceDebug(aroundContext.logService, endTime - startTime,
						aroundContext.builder.message("{} returned value {}", aroundContext.methodSignature,
								this.resultToString(result, aroundContext.showArgValues)));
			} else {
				LogUtil.performanceInfo(aroundContext.logService, endTime - startTime,
						aroundContext.builder.message("{} returned", aroundContext.methodSignature));
			}
		} else if (aroundContext.debug) {
			LogUtil.debug(aroundContext.logService, aroundContext.builder.message("{} returned value {}",
					aroundContext.methodSignature, this.resultToString(result, aroundContext.showArgValues)));
		}
	}

	@Override
	protected void doAfterThrowable(ProceedingJoinPoint pjp, Loggable context, AroundContext aroundContext,
			Throwable throwable, long startTime, long endTime) {
		if (aroundContext.logService.isInfoEnabled() && aroundContext.perf) {
			if (aroundContext.debug) {
				LogUtil.performanceDebug(aroundContext.logService, endTime - startTime,
						aroundContext.builder.throwable(throwable, "{} thrown {}", aroundContext.methodSignature,
								throwable.getClass().getSimpleName()));
			} else {
				LogUtil.performanceInfo(aroundContext.logService, endTime - startTime, aroundContext.builder
						.throwable(throwable, "{} thrown exception", aroundContext.methodSignature));
			}
		} else if (aroundContext.debug) {
			LogUtil.debug(aroundContext.logService, aroundContext.builder.throwable(throwable, "{} thrown {}",
					aroundContext.methodSignature, throwable.getClass().getSimpleName()));
		}
	}

	@Override
	protected void doFinally(ProceedingJoinPoint pjp, Loggable context, AroundContext beforeResult) {
		// nop in this implementation
	}

	/**
	 * Return the appropriate {@link LogService} to be used for logging, retrieving
	 * if by calling {@link LogServiceFactory#getLogger(Class)}, based on the class
	 * of the aspect target {@link ProceedingJoinPoint#getTarget()} if the target is
	 * not null, or this class if target is null.
	 * 
	 * @param pjp the join point to proceed
	 * @return the appropriate {@link LogService} to be used for logging, based on
	 *         the class of the aspect target
	 *         {@link ProceedingJoinPoint#getTarget()} if the target is not null, or
	 *         this class if target is null
	 */
	protected Logger getLogService(ProceedingJoinPoint pjp) {
		final Logger result;
		if (pjp.getTarget() == null) {
			// not cool
			result = LoggerFactory.getLogger(LoggableAspectBaseSupport.class);
			result.warn("Unable to find proceeding join point target. This should not happen.");
		} else {
			// cool
			result = LoggerFactory.getLogger(pjp.getTarget().getClass());
		}
		return result;
	}

	/**
	 * If given loggable is null, search within
	 * {@link ProceedingJoinPoint#getSignature()} if we can retrieve annotation data
	 * thru java reflect on method's declaring class.
	 * 
	 * @param pjp      the join point to proceed
	 * @param loggable annotation data
	 * @return given loggable if it is not null else proceeding method's declaring
	 *         class {@link Loggable} annotation if it exists or null
	 */
	protected Loggable contextForTypeLevelAnnotation(ProceedingJoinPoint pjp, Loggable loggable) {

		final Loggable result;
		if (loggable == null && pjp != null && pjp.getSignature() instanceof MethodSignature) {
			MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
			result = methodSignature.getMethod().getDeclaringClass().getAnnotation(Loggable.class);
		} else {
			result = loggable;
		}
		return result;
	}

	/**
	 * Search within {@link ProceedingJoinPoint#getSignature()} if we can retrieve
	 * annotation data thru java reflect on method's declaring class and return it.
	 * 
	 * @param pjp the join point to proceed
	 * @return class level {@link Loggable} annotation if any otherwise null
	 */
	protected Loggable getClassLevelLoggable(ProceedingJoinPoint pjp) {
		final Loggable result;
		if (pjp != null && pjp.getSignature() instanceof MethodSignature) {
			MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
			result = methodSignature.getMethod().getDeclaringClass().getAnnotation(Loggable.class);
		} else {
			result = null;
		}
		return result;
	}

	protected int getLoggableSignatureType(Loggable methodContext, Loggable classContext) {
		final int classLevelValue = classContext == null ? Loggable.SIGNATURE_TYPE_DEFAULT_VALUE
				: classContext.signatureType();
		return methodContext == null ? classLevelValue
				: methodContext.signatureType() == Loggable.SIGNATURE_TYPE_DEFAULT_VALUE ? classLevelValue
						: methodContext.signatureType();
	}

	protected boolean getLoggablePerf(Loggable methodContext, Loggable classContext) {
		final boolean classLevelValue = classContext == null ? Loggable.PERF_DEFAULT_VALUE : classContext.perf();
		return methodContext == null ? classLevelValue
				: methodContext.perf() == Loggable.PERF_DEFAULT_VALUE ? classLevelValue : methodContext.perf();
	}

	protected boolean getLoggableDebug(Loggable methodContext, Loggable classContext) {
		final boolean classLevelValue = classContext == null ? Loggable.DEBUG_DEFAULT_VALUE : classContext.debug();
		return methodContext == null ? classLevelValue
				: methodContext.debug() == Loggable.DEBUG_DEFAULT_VALUE ? classLevelValue : methodContext.debug();
	}

	protected boolean getLoggableShowArgValues(Loggable methodContext, Loggable classContext) {
		final boolean classLevelValue = classContext == null ? Loggable.SHOW_ARG_VALUES_DEFAULT_VALUE
				: classContext.showArgValues();
		return methodContext == null ? classLevelValue
				: methodContext.showArgValues() == Loggable.SHOW_ARG_VALUES_DEFAULT_VALUE ? classLevelValue
						: methodContext.showArgValues();
	}

	protected String getLoggableService(Loggable methodContext, Loggable classContext) {
		final String classLevelValue = classContext == null ? Loggable.SERVICE_DEFAULT_VALUE : classContext.service();
		return methodContext == null ? classLevelValue
				: methodContext.service() == Loggable.SERVICE_DEFAULT_VALUE ? classLevelValue : methodContext.service();
	}

	/**
	 * Simple class holding data to be passed to
	 * {@link LoggableAspectBaseSupport#doAfterReturning(ProceedingJoinPoint, Loggable, AroundContext, Object, long, long)}
	 * ,
	 * {@link LoggableAspectBaseSupport#doAfterThrowable(ProceedingJoinPoint, Loggable, AroundContext, Throwable, long, long)}
	 * and
	 * {@link LoggableAspectBaseSupport#doFinally(ProceedingJoinPoint, Loggable, AroundContext)}
	 * 
	 * @author Atos
	 */
	protected static class AroundContext {

		/** a builder for {@link LogData} */
		private LogDataBuilder builder;

		/** the {@link LogService} to be used for logging */
		private Logger logService;

		/** the method signature to be used for automatic debug or perf logging */
		private String methodSignature;

		private boolean debug;

		private boolean perf;

		private boolean showArgValues;

		/**
		 * Build a new instance for given arguments
		 * 
		 * @param builder         the log data builder to
		 * @param logService      the logService to use
		 * @param methodSignature the method signature to use in the message
		 * @param debug           is debug enabled
		 * @param perf            is perf enabled
		 * @param showArgValues   should we render method argument and return values
		 */
		protected AroundContext(LogDataBuilder builder, Logger logService, String methodSignature, boolean debug,
				boolean perf, boolean showArgValues) {
			super();
			this.builder = builder;
			this.logService = logService;
			this.methodSignature = methodSignature;
			this.debug = debug;
			this.perf = perf;
			this.showArgValues = showArgValues;
		}

	}
}
