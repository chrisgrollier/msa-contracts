package net.chrisgrollier.cloud.apps.common.i18n;

import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Interface of beans capable to render formatted and localized message based on
 * a {@link String} key and a variable list of arguments.
 * 
 * @author Atos
 *
 */
public interface MessageManager {

	/**
	 * Return an array holding all locales supported by the application.
	 * 
	 * @return an array holding all locales supported by the application
	 */
	Locale[] getSupportedLocales();

	/**
	 * Return a message based on the given key, with the given arguments applied,
	 * using the default {@link Locale}. The default implementation delegates to
	 * {@link #getMessage(String, Locale, Object...)}, using
	 * {@link LocaleContextHolder#getLocale()} as {@link Locale}.
	 * 
	 * @param key
	 *            key of the message
	 * @param args
	 *            arguments to apply to the message
	 * @return a message based on given key and arguments, using a default
	 *         {@link Locale}
	 */
	default String getMessage(String key, Object... args) {
		return this.getMessage(key, LocaleContextHolder.getLocale(), args);
	}

	/**
	 * Return a message based on the given key, with the given arguments applied,
	 * using the given locale.
	 * 
	 * @param key
	 *            key of the message
	 * @param locale
	 *            locale to be used
	 * @param args
	 *            arguments to apply to the message
	 * @return a message based on given key, arguments and {@link Locale}
	 */
	String getMessage(String key, Locale locale, Object... args);

	/**
	 * Return a message based on the given {@link MessageSourceResolvable} using the
	 * default {@link Locale}. The default implementation delegates to
	 * {@link #getMessage(MessageSourceResolvable, Locale)}, using
	 * {@link LocaleContextHolder#getLocale()} as {@link Locale}
	 * 
	 * @param messageSourceResolvable
	 *            a {@link MessageSourceResolvable}
	 * @return a message based on the given {@link MessageSourceResolvable}, using
	 *         {@link LocaleContextHolder#getLocale()} as {@link Locale}.
	 */
	default String getMessage(MessageSourceResolvable messageSourceResolvable) {
		return this.getMessage(messageSourceResolvable, LocaleContextHolder.getLocale());
	}

	/**
	 * Return a message based on the given {@link MessageSourceResolvable} using the
	 * given locale.
	 * 
	 * @param messageSourceResolvable
	 *            a {@link MessageSourceResolvable}
	 * @param locale
	 *            a {@link Locale}
	 * @return a message based on the given {@link MessageSourceResolvable}, using
	 *         the given {@link Locale}
	 */
	String getMessage(MessageSourceResolvable messageSourceResolvable, Locale locale);

	/**
	 * Return a {@link Map} of {@link String} message indexed by {@link Locale}
	 * based on a given key, with the given arguments applied, using a previously
	 * registered list of {@link Locale}. In other words, return different localized
	 * versions of the same message based on a given key, with the given arguments
	 * applied for each {@link Locale} previously registered by the application. The
	 * default implementation delegates to
	 * {@link #getAllMessages(String, Locale[], Object...)} using
	 * {@link #getSupportedLocales()} as array of {@link Locale}.
	 * 
	 * @param key
	 *            key of the message
	 * @param args
	 *            arguments to apply to the message
	 * @return a {@link Map} of {@link String} messages indexed by {@link Locale}
	 *         representing different localized versions of the same message based
	 *         on a given key, with the given arguments applied for each
	 *         {@link Locale} previously registered by the application
	 */
	default Map<Locale, String> getAllMessages(String key, Object... args) {
		return this.getAllMessages(key, getSupportedLocales(), args);
	}

	/**
	 * Return a {@link Map} of {@link String} message indexed by {@link Locale}
	 * based on a given key, with the given arguments applied, using a given list of
	 * {@link Locale}. In other words, return different localized versions of
	 * message based on a given key, with the given arguments applied for each
	 * {@link Locale} in the given {@link Locale} array.
	 * 
	 * @param key
	 *            key of the message
	 * @param locales
	 *            an array of {@link Locale}
	 * @param args
	 *            arguments to apply to the message
	 * @return a {@link Map} of {@link String} messages indexed by {@link Locale}
	 *         representing different localized versions of the same message based
	 *         on a given key, with the given arguments applied for each
	 *         {@link Locale} in the given {@link Locale} array
	 * 
	 */
	Map<Locale, String> getAllMessages(String key, Locale[] locales, Object... args);

	/**
	 * Return a {@link Map} of {@link String} message indexed by {@link Locale}
	 * based on a given {@link MessageSourceResolvable}, using a previously
	 * registered list of {@link Locale}. In other words, return different localized
	 * versions of the same message based on a given {@link MessageSourceResolvable}
	 * for each {@link Locale} previously registered by the application. The default
	 * implementation delegates to
	 * {@link #getAllMessages(MessageSourceResolvable, Locale[])} using
	 * {@link #getSupportedLocales()} as array of {@link Locale}.
	 * 
	 * @param messageSourceResolvable
	 *            a {@link MessageSourceResolvable}
	 * @return a {@link Map} of {@link String} message indexed by {@link Locale}
	 *         based on a given {@link MessageSourceResolvable}, using a previously
	 *         registered list of {@link Locale}
	 */
	default Map<Locale, String> getAllMessages(MessageSourceResolvable messageSourceResolvable) {
		return this.getAllMessages(messageSourceResolvable, this.getSupportedLocales());
	}

	/**
	 * Return a {@link Map} of {@link String} message indexed by {@link Locale}
	 * based on a given {@link MessageSourceResolvable}, using a given list of
	 * {@link Locale}. In other words, return different localized versions of
	 * message based on a given {@link MessageSourceResolvable} for each
	 * {@link Locale} in the given {@link Locale} array.
	 * 
	 * @param messageSourceResolvable
	 *            a {@link MessageSourceResolvable}
	 * @param locales
	 *            an array of {@link Locale}
	 * @return a {@link Map} of {@link String} message indexed by {@link Locale}
	 *         based on a given {@link MessageSourceResolvable}, using a given list
	 *         of {@link Locale}
	 */
	Map<Locale, String> getAllMessages(MessageSourceResolvable messageSourceResolvable, Locale[] locales);
}
