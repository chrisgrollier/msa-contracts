package net.chrisgrollier.cloud.apps.common.util.mapping;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Utility interface for bidirectional object mapping. Provides methods to map
 * objects of 2 classes in two directions. In other words, can transform objects
 * of class S in objects of class T and vice versa. Also provides default
 * methods to map collections and iterable of these classes.
 * 
 * @author Atos
 *
 * @param T
 *            class of the object returned by from methods
 * @param S
 *            class of the object returned by to methods
 */
public interface BidiMapper<T, S> {

	/**
	 * Copy attributes from an instance of S to an instance of T
	 * 
	 * @param t
	 *            the instance of T
	 * @param s
	 *            the instance of S
	 * @return the instance of T
	 */
	T copyFrom(T t, S s);

	/**
	 * Creates and return an instance of T based on an object of class S.
	 * 
	 * @param s
	 *            an instance of S
	 * @return an instance of T
	 */
	T from(S s);

	/**
	 * Copy attributes of an instance of T to an instance of S
	 * 
	 * @param t
	 *            the instance of T
	 * @param s
	 *            the instance of S
	 * @return the instance of S
	 */
	S copyTo(T t, S s);

	/**
	 * Creates and return an instance of S based on an object of class T.
	 * 
	 * @param t
	 *            an instance of T
	 * @return an instance of S
	 */
	S to(T t);

	/**
	 * Return a collection of S instances based on the collection of T. Individual
	 * mapping relies on {@link #to(Object)}.
	 * 
	 * @param t
	 *            the collection of T instances
	 * @return a collection of S instances
	 */
	default Collection<S> tos(Collection<T> t) {
		return t.stream().map(o -> to(o)).collect(Collectors.toList());
	}

	/**
	 * Return a collection of T instances based on the collection of S. Individual
	 * mapping relies on {@link #from(Object)}.
	 * 
	 * @param s
	 *            the collection of S instances
	 * @return a collection of T instances
	 */
	default Collection<T> froms(Collection<S> s) {
		return s.stream().map(o -> from(o)).collect(Collectors.toList());
	}

	/**
	 * Return a collection of S instances based on an {@link Iterable} of T.
	 * Individual mapping relies on {@link #to(Object)}.
	 * 
	 * @param t
	 *            the iterable of T
	 * @return a collection of S instances
	 */
	default Collection<S> tos(Iterable<T> t) {
		return StreamSupport.stream(t.spliterator(), false).map(o -> to(o)).collect(Collectors.toList());
	}

	/**
	 * Return a collection of T instances based on an {@link Iterable} S. Individual
	 * mapping relies on {@link #from(Object)}.
	 * 
	 * @param s
	 *            the iterable of S instances
	 * @return a collection of T instances
	 */
	default Collection<T> froms(Iterable<S> s) {
		return StreamSupport.stream(s.spliterator(), false).map(o -> from(o)).collect(Collectors.toList());
	}
}
