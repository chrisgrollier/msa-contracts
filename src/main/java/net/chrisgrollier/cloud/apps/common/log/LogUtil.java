package net.chrisgrollier.cloud.apps.common.log;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.MDC;

import com.google.common.collect.Maps;

import net.chrisgrollier.cloud.apps.common.exception.FrameworkFunctionalException;
import net.chrisgrollier.cloud.apps.common.log.LogData.LogDataBuilder;

public class LogUtil {

	public static void business(Logger logger, String message, Object... args) {
		business(logger, new HashMap<>(), message, args);
	}

	public static void business(Logger logger, Map<String, String> context, String message, Object... args) {
		business(logger, LogData.currentBuilder().message(message, args).context(context));
	}

	public static void business(Logger logger, LogDataBuilder builder) {
		info(logger, builder, LogType.BUSINESS, null);
	}

	public static void performanceInfo(Logger logger, long duration, String message, Object... args) {
		performanceInfo(logger, duration, LogData.currentBuilder().message(message, args));
	}

	public static void performanceInfo(Logger logger, long duration, LogDataBuilder builder) {
		info(logger, builder, LogType.PERF, duration);
	}

	public static void performanceDebug(Logger logger, long duration, String message, Object... args) {
		performanceDebug(logger, duration, LogData.currentBuilder().message(message, args));
	}

	public static void performanceDebug(Logger logger, long duration, LogDataBuilder builder) {
		debug(logger, builder, LogType.PERF, duration);
	}

	public static void debug(Logger logger, String message, Object... args) {
		debug(logger, LogData.currentBuilder().message(message, args));
	}

	public static void debug(Logger logger, LogData.LogDataBuilder builder) {
		debug(logger, builder, LogType.TECH, null);
	}

	private static void debug(Logger logger, LogData.LogDataBuilder builder, LogType logType, Long duration) {
		if (logger.isDebugEnabled()) {
			LogData logData = builder.build();
			setParametersInMDC(logType, logData, duration);
			String line = Optional.ofNullable(logData.getMessage()).orElse(StringUtils.EMPTY);
			final Throwable throwable = logData.getThrowable();
			if (throwable != null && !(throwable instanceof FrameworkFunctionalException)) {
				String stacktrace = formatStackTrace(throwable);
				line += "#" + stacktrace;
			}
			logger.debug(line, logData.getMessageArgs());
			releaseMdcContextMap();
		}
	}

	private static void info(Logger logger, LogData.LogDataBuilder builder, LogType logType, Long duration) {
		if (logger.isInfoEnabled()) {
			LogData logData = builder.build();
			setParametersInMDC(logType, logData, duration);
			String line = Optional.ofNullable(logData.getMessage()).orElse(StringUtils.EMPTY);
			final Throwable throwable = logData.getThrowable();
			if (throwable != null && !(throwable instanceof FrameworkFunctionalException)) {
				String stacktrace = formatStackTrace(throwable);
				line += "#" + stacktrace;
			}
			logger.info(line, logData.getMessageArgs());
			releaseMdcContextMap();

		}
	}

	/**
	 * Fills Mapped Diagnostic Context (MDC) with data provided by the logData
	 * argument.
	 * 
	 * @param logType  the log type
	 * @param logData  the log data
	 * @param duration the duration for performance log. can be null
	 */

	private static void setParametersInMDC(final LogType logType, final LogData logData, final Long duration) {
		MDC.put(LoggerParameter.LOG_TYPE.toString(), logType.name());

		final Map<String, String> context = logData.getContext();
		if (context != null) {
			MDC.put(LoggerParameter.CONTEXT.toString(), mapToString(context));
		}

		final String service = logData.getService();
		if (service != null) {
			MDC.put(LoggerParameter.SERVICE.toString(), service);
		}

		if (logType == LogType.PERF && duration != null) {
			MDC.put(LoggerParameter.DURATION.toString(), duration.toString());

		}
	}

	/**
	 * Convert the given map to String as key=value with # as separator.
	 * 
	 * @param map map of string values to be converted
	 * @return a String corresponding to the converted map
	 */
	private static String mapToString(Map<String, String> map) {
		// @formatter:off
		return Optional.ofNullable(map).orElse(Maps.newHashMap()).entrySet().stream()
				.map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining("#"));
		// @formatter:on
	}

	/**
	 * Release log4j MDC context except spring-sleuth parameters. These parameters
	 * are also put in slf4j MDC so beware of not removing them
	 */
	private static void releaseMdcContextMap() {

		// @formatter:off
		Optional.ofNullable(MDC.getCopyOfContextMap()).orElse(Maps.newHashMap()).keySet().stream()
				.filter(p -> LoggerParameter.fromString(p) != null).forEach(MDC::remove);
		// @formatter:on
	}

	/**
	 * Format stackTrace as String. All stack trace element will be concatenated
	 * with # as separator.
	 * 
	 * @param throwable the throwable exception to be formated
	 * @return formated stackTrace as String.
	 */
	private static String formatStackTrace(Throwable throwable) {
		// @formatter:off
		String stacktrace = Stream.of(throwable.getStackTrace()).map(StackTraceElement::toString)
				.collect(Collectors.joining("#"));
		return throwable.getMessage() + "#" + stacktrace;
		// @formatter:on
	}
}
