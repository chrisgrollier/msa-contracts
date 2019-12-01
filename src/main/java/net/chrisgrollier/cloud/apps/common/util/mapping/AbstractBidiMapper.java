package net.chrisgrollier.cloud.apps.common.util.mapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract implementation of the {@link BidiMapper} interface providing basic
 * implementation for {@link BidiMapper#from(Object)} and for
 * {@link BidiMapper#to(Object)} methods. Subclasses should only implement
 * {@link BidiMapper#copyFrom(Object, Object)} and
 * {@link BidiMapper#copyTo(Object, Object)} and a default constructor that
 * should invoke {@link #AbstractBidiMapper(Class, Class)}.
 * 
 * @author Atos
 *
 * @param T
 *            class of the object returned by from methods
 * @param S
 *            class of the object returned by to methods
 */
public abstract class AbstractBidiMapper<T, S> implements BidiMapper<T, S> {
	
	/** a log service for all subclasses */
	protected final Logger logService = LoggerFactory.getLogger(this.getClass());

	/** concrete class of T */
	private final Class<T> tClass;

	/** concrete class of S */
	private final Class<S> sClass;

	/**
	 * Constructor that shoud be inkoed by subclasses with arguments T.class and
	 * S.class
	 * 
	 * @param tClass
	 *            the class of T
	 * @param sClass
	 *            the class of S
	 */
	protected AbstractBidiMapper(Class<T> tClass, Class<S> sClass) {
		super();
		this.tClass = tClass;
		this.sClass = sClass;
	}

	@Override
	public T from(S s) {
		return this.copyFrom(this.newT(), s);
	}

	@Override
	public S to(T t) {
		return this.copyTo(t, this.newS());
	}

	/**
	 * Factory method for class T called by {@link #from(Object)} method. Subclasses
	 * may override if class T does not provide a default public constructor with no
	 * args.
	 * 
	 * @return a new instance of T
	 */
	protected T newT() {
		return newInstance(this.tClass);
	}

	/**
	 * Factory method for class S called by {@link #from(Object)} method. Subclasses
	 * may override if class S does not provide a default public constructor with no
	 * args.
	 * 
	 * @return a new instance of S
	 */
	protected S newS() {
		return newInstance(this.sClass);
	}

	/**
	 * Return a new instance of the provided class by invoking the
	 * {@link Class#newInstance()} method.
	 * 
	 * @param clazz
	 *            the class of the object to be instantiated.
	 * @return a new instance of the provided class
	 */
	private <C> C newInstance(Class<C> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logService.error("newInstance", e, "Unable to instanciate class {}", clazz.getName());
			throw new RuntimeException("Unable to instanciate class " + clazz.getName()
					+ ". This class should implement a public constructor with no args", e);
		}
	}
}
