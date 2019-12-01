package net.chrisgrollier.cloud.apps.common.util.mapping.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.CodeSignature;

/**
 * Abstract class defining common behavior for aspects that are defined around
 * methods.
 * 
 * @author Atos
 *
 * @param <C>
 *            a class carrying contextual data about the actual aspect, usually
 *            aspect arguments
 * @param <B>
 *            a class carrying data generated before calling the adviced method,
 *            returned by {@link #doBefore(ProceedingJoinPoint, Object)} and
 *            passed to
 *            {@link #doAfterReturning(ProceedingJoinPoint, Object, Object, Object, long, long)}
 *            and
 *            {@link #doAfterThrowable(ProceedingJoinPoint, Object, Object, Throwable, long, long)}
 *            methods.
 * @see ProceedingJoinPoint
 */
public abstract class AbstractAspect<C, B> {

	/**
	 * Basic strategy method that should be called by actual aspect annotated by
	 * {@link Around} methods. Calls {@link #doBefore(ProceedingJoinPoint, Object)}
	 * passing proceeding join point data and context then proceeds the join point
	 * then calls
	 * {@link #doAfterReturning(ProceedingJoinPoint, Object, Object, Object, long, long)}
	 * and return join point result if join point returned without exception or
	 * calls
	 * {@link #doAfterThrowable(ProceedingJoinPoint, Object, Object, Throwable, long, long)}
	 * and re-throw caught {@link Throwable} in case of error.
	 * 
	 * @param pjp
	 *            {@link Around} AJ advice data
	 * @param context
	 *            advice contextual data such as advice args
	 * @return object return by {@link ProceedingJoinPoint#proceed()} method
	 * @throws Throwable
	 *             exception eventually thrown by by
	 *             {@link ProceedingJoinPoint#proceed()} method
	 */
	protected Object simpleAroundStrategy(ProceedingJoinPoint pjp, C context) throws Throwable {
		// call doBefore
		B aroundContext = this.doBefore(pjp, context);
		// check time
		long startTime = System.currentTimeMillis();
		try {
			// proceed
			Object result = pjp.proceed();
			// call afterReturning
			this.doAfterReturning(pjp, context, aroundContext, result, startTime, System.currentTimeMillis());
			// return proceed result
			return result;
		} catch (Throwable throwable) {
			// call afterThrowable
			this.doAfterThrowable(pjp, context, aroundContext, throwable, startTime, System.currentTimeMillis());
			// rethrow
			throw throwable;
		} finally {
			this.doFinally(pjp, context, aroundContext);
		}
	}

	/**
	 * Return the join point signature as a string, using appropriate toString()
	 * method depending on type arg value.
	 * 
	 * @param pjp
	 *            join point to work with
	 * @param type
	 *            an int allowing to choose which toString method to use (see return
	 *            doc)
	 * @return if type == 0 {@link Signature#toString()}, < 0
	 *         {@link Signature#toShortString()} else
	 *         {@link Signature#toLongString()}
	 */
	protected final String getMethodSignature(ProceedingJoinPoint pjp, int type) {
		final Signature signature = pjp.getSignature();
		return type > 0 ? signature.toLongString() : type == 0 ? signature.toString() : signature.toShortString();
	}

	/**
	 * Utility method rendering if necessary arguments passed to the
	 * {@link ProceedingJoinPoint}.
	 * 
	 * @param pjp
	 *            the join point holding args to render
	 * @param showArgValues
	 *            indicates if args shoud be rendered or hidden
	 * @return the rendered string
	 * 
	 * @See ProceedingJoinPoint#getArgs()
	 */
	protected final String argsToString(ProceedingJoinPoint pjp, boolean showArgValues) {
		final StringBuilder builder = new StringBuilder();
		if (showArgValues) {
			final CodeSignature codeSignature = pjp.getSignature() instanceof CodeSignature
					? (CodeSignature) pjp.getSignature()
					: null;
			int count = 0;
			for (Object object : pjp.getArgs()) {
				builder.append(count == 0 ? "" : ", ")
						.append(codeSignature != null ? codeSignature.getParameterNames()[count] : "")
						.append(codeSignature != null ? "=" : "");
				if (object instanceof Object[]) {
					builder.append("[");
					Object[] subArgs = (Object[]) object;
					for (Object subArg : subArgs) {
						builder.append(count == 0 ? "" : ", ").append(
								subArg == null ? subArg : subArg instanceof Object[] ? ".." : subArg.toString());
					}
					builder.append(']');
				} else {
					builder.append(object == null ? "null" : object.toString());
				}
				count++;
			}
		} else {
			builder.append("******");
		}
		return builder.toString();
	}

	/**
	 * 
	 * Utility method rendering if necessary result returned by
	 * {@link ProceedingJoinPoint#proceed()}
	 * 
	 * @param result
	 *            the object returned by {@link ProceedingJoinPoint#proceed()}
	 * @param showArgValues
	 *            indicates if args shoud be rendered or hidden
	 * @return the rendered string
	 * 
	 */
	protected final String resultToString(Object result, boolean showArgValues) {
		final StringBuilder builder = new StringBuilder();
		if (showArgValues) {
			if (result instanceof Object[]) {
				builder.append("[");
				Object[] results = (Object[]) result;
				long count = 0;
				for (Object object : results) {
					builder.append(count == 0 ? "" : ", ")
							.append(object == null ? object : object instanceof Object[] ? ".." : object.toString());
				}
				builder.append(']');
			} else {
				builder.append(result != null ? result.toString() : result);
			}
		} else {
			builder.append("******");
		}
		return builder.toString();
	}

	/**
	 * Abstract method called by
	 * {@link #simpleAroundStrategy(ProceedingJoinPoint, Object)} before calling
	 * {@link ProceedingJoinPoint#proceed()}. Should return an instance of B class
	 * which should contain any data required by
	 * {@link #doAfterReturning(ProceedingJoinPoint, Object, Object, Object, long, long)}
	 * and
	 * {@link #doAfterThrowable(ProceedingJoinPoint, Object, Object, Throwable, long, long)}
	 * methods.
	 * 
	 * @param pjp
	 *            join point to proceed
	 * @param context
	 *            context passed by aspect
	 * @return an new instance of B
	 */
	protected abstract B doBefore(ProceedingJoinPoint pjp, C context);

	/**
	 * Abstract method called by
	 * {@link #simpleAroundStrategy(ProceedingJoinPoint, Object)} after calling
	 * {@link ProceedingJoinPoint#proceed()}.
	 * 
	 * @param pjp
	 *            proceeded join point
	 * @param context
	 *            aspect context
	 * @param beforeResult
	 *            around context
	 * @param result
	 *            result of call to {@link ProceedingJoinPoint#proceed()}
	 * @param startTime
	 *            time computed before call
	 * @param endTime
	 *            time computed after call
	 */
	protected abstract void doAfterReturning(ProceedingJoinPoint pjp, C context, B beforeResult, Object result,
			long startTime, long endTime);

	/**
	 * Abstract method called by
	 * {@link #simpleAroundStrategy(ProceedingJoinPoint, Object)} after calling
	 * {@link ProceedingJoinPoint#proceed()}.
	 * 
	 * @param pjp
	 *            proceeded join point
	 * @param context
	 *            aspect context
	 * @param beforeResult
	 *            around context
	 * @param throwable
	 *            {@link Throwable} eventually thrown by call to
	 *            {@link ProceedingJoinPoint#proceed()}
	 * @param startTime
	 *            time computed before call
	 * @param endTime
	 *            time computed after call
	 */
	protected abstract void doAfterThrowable(ProceedingJoinPoint pjp, C context, B beforeResult, Throwable throwable,
			long startTime, long endTime);

	/**
	 * 
	 * @param pjp
	 * @param context
	 * @param beforeResult
	 */
	protected abstract void doFinally(ProceedingJoinPoint pjp, C context, B beforeResult);

}
