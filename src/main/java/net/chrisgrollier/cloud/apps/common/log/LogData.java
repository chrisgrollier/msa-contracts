package net.chrisgrollier.cloud.apps.common.log;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * A representation of data to be logged by a LogService.
 */
public class LogData {

	private static final ThreadLocal<Deque<LogDataBuilder>> logDataBuilderHolder = ThreadLocal.withInitial(() -> {
		Deque<LogDataBuilder> deque = new ArrayDeque<>();
		deque.push(new LogDataBuilder("thread default service"));
		return deque;
	});

	/** The name of the service/method producing the log */
	private final String service;

	/** The detail message or pattern to be logged */
	private final String message;

	/** The message args if the message is a pattern **/
	private final Object[] messageArgs;

	/** The execution context as keys=value with # as separator */
	private final Map<String, String> context;

	/** A throwable in case of exception logging **/
	private final Throwable throwable;

	/**
	 * A private constructor to prevent direct instantiation and thus enforce usage
	 * of {@link LogDataBuilder}.
	 * 
	 * @param builder
	 *            the builder
	 */
	private LogData(LogDataBuilder builder) {
		this.service = checkNotNull(builder.service, "Service name is required");
		this.message = checkNotNull(builder.message, "Log message is required");
		this.messageArgs = builder.messageArgs;
		this.context = builder.context;
		this.throwable = builder.throwable;
	}

	/**
	 * Return a new {@link LogDataBuilder} with given service applied.
	 * 
	 * @param service
	 *            the service
	 * @return a builder with given service applied
	 */
	public static LogDataBuilder forService(final String service) {
		return new LogDataBuilder(service);
	}

	/**
	 * Return a new {@link LogDataBuilder} for "normal" tracing purpose.
	 * 
	 * @param service
	 *            the service
	 * @param message
	 *            the message or pattern
	 * @param args
	 *            message arguments
	 * @return a builder for "normal" tracing purpose
	 */
	public static LogDataBuilder forTrace(final String service, final String message, final Object... args) {
		return new LogDataBuilder(service).message(message, args);
	}

	/**
	 * Return a new {@link LogDataBuilder} for "exception" tracing purpose.
	 * 
	 * @param service
	 *            the service
	 * @param throwable
	 *            the exception that cause the log
	 * @param message
	 *            the message or pattern
	 * @param args
	 *            message arguments
	 * @return a builder for "error" tracing purpose
	 */
	public static LogDataBuilder forError(final String service, final Throwable throwable, final String message,
			final Object... args) {
		return new LogDataBuilder(service).throwable(throwable, message, args);
	}

	/**
	 * Reinit current thread builder {@link Deque}.
	 */
	public static void resetCurrentBuilder() {
		logDataBuilderHolder.remove();
	}

	/**
	 * Return current thread {@link Deque} head builder
	 * 
	 * @return current thread {@link Deque} head builder
	 */
	public static LogDataBuilder currentBuilder() {
		return logDataBuilderHolder.get().peek();
	}

	/**
	 * Return the service/method field.
	 * 
	 * @return the service/method field.
	 */
	public String getService() {
		return service;
	}

	/**
	 * Return the message or pattern.
	 * 
	 * @return the message or pattern.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Return the message arguments.
	 * 
	 * @return the message arguments.
	 */
	public Object[] getMessageArgs() {
		return messageArgs;
	}

	/**
	 * Return the log caller context.
	 * 
	 * @return the log caller context
	 */
	public Map<String, String> getContext() {
		return Collections.unmodifiableMap(context);
	}

	/**
	 * Return the {@link Throwable} that caused the log.
	 * 
	 * @return the {@link Throwable} that caused the log
	 */
	public Throwable getThrowable() {
		return throwable;
	}

	/**
	 * A fluent builder for logger data, service name and message attributes are
	 * mandatory.
	 * 
	 * @author Atos
	 *
	 */
	public static class LogDataBuilder {

		private String service;
		private String message;
		private Object[] messageArgs;
		private final Map<String, String> context = Maps.newHashMap();
		private Throwable throwable;

		private LogDataBuilder(final String service) {
			super();
			this.service = service;
		}

		public LogDataBuilder service(String service) {
			if (service != null && service.trim().length() > 0) {
				this.service = service;
			}
			return this;
		}

		/**
		 * Defines message and its eventual arguments (if message is a pattern)
		 * 
		 * @param message
		 *            the message or message pattern
		 * @param args
		 *            eventual arguments
		 * @return this builder
		 */
		public LogDataBuilder message(final String message, final Object... args) {
			this.message = message;
			this.messageArgs = args;
			return this;
		}

		/**
		 * Defines the context map
		 * 
		 * @param context
		 *            the new value
		 * @return this builder
		 */
		public LogDataBuilder context(final Map<String, String> context) {
			this.context.putAll(context);
			return this;
		}

		/**
		 * Adds a context item in the context map
		 * 
		 * @param key
		 *            key of the item
		 * @param value
		 *            value for the item
		 * @return this builder
		 */
		public LogDataBuilder putContextItem(final String key, final String value) {
			this.context.put(key, value);
			return this;
		}

		/**
		 * Defines the throwable and related message with its eventual arguments (if
		 * message is a pattern)
		 * 
		 * @param throwable
		 *            a throwable caught by the application
		 * @param message
		 *            the message or message pattern
		 * @param args
		 *            eventual arguments
		 * @return this builder
		 */
		public LogDataBuilder throwable(final Throwable throwable, final String message, final Object... args) {
			this.throwable = throwable;
			this.message = message;
			this.messageArgs = args;
			return this;
		}

		/**
		 * Return a new {@link LogData} based on this builder.
		 * 
		 * @return a new {@link LogData} based on this builder
		 */
		public LogData build() {
			final LogData result = new LogData(this);
			this.reset();
			return result;
		}

		/**
		 * Reset builder's attributes, except service and context.
		 */
		private void reset() {
			this.throwable = null;
			this.message = null;
			this.messageArgs = null;
		}
	}
}
