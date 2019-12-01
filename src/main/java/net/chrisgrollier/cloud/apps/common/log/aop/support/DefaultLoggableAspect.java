package net.chrisgrollier.cloud.apps.common.log.aop.support;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import net.chrisgrollier.cloud.apps.common.log.aop.Loggable;

/**
 * Aspect handling performance and debug logging. Is enabled with annotation
 * {@link Loggable}.
 * 
 * @author Atos
 *
 */
@Aspect
public class DefaultLoggableAspect extends LoggableAspectBaseSupport {

	/**
	 * Build a new instance.
	 */
	@Autowired
	public DefaultLoggableAspect() {
		super();
	}

	/**
	 * Defines an aspect on execution of any method annotated by {@link Loggable} or
	 * any method of any class within sub-packages of "com.inetpsa" annotated by
	 * {@link Loggable}.
	 * 
	 * @param pjp
	 *            proceeding join point
	 * @param loggable
	 *            found annotation
	 * @return result of proceeded method if no exception was thrown
	 * @throws Throwable
	 *             exception thrown while proceeding
	 */
	@Around("isNetChrisGrollierAnyExecution() && isLoggable(loggable))")
	public Object logArroundLoggable(ProceedingJoinPoint pjp, Loggable loggable) throws Throwable {
		return this.simpleAroundStrategy(pjp, loggable);
	}
}
