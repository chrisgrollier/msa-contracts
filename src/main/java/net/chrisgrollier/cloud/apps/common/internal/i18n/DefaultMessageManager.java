package net.chrisgrollier.cloud.apps.common.internal.i18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import net.chrisgrollier.cloud.apps.common.i18n.MessageManager;

/**
 * Default implementation of {@link MessageManager} based on a
 * {@link MessageSource} relying on {@link LocaleContextHolder#getLocale()} when
 * no Locale is specified
 * 
 * @author Atos
 *
 */
public class DefaultMessageManager implements MessageManager {

	/** The message source holding application messages */
	private final MessageSource messageSource;

	/** The array of locales supported by the application */
	private final Locale[] supportedLocales;

	@Autowired
	public DefaultMessageManager(MessageSource messageSource) {
		this(messageSource, new Locale[] { LocaleContextHolder.getLocale() });
	}

	@Autowired
	public DefaultMessageManager(MessageSource messageSource, Locale[] supportedLocales) {
		this.messageSource = messageSource;
		this.supportedLocales = supportedLocales;
	}

	@Override
	public Locale[] getSupportedLocales() {
		return supportedLocales;
	}

	@Override
	public String getMessage(String key, Locale locale, Object... args) {
		return this.messageSource.getMessage(key, args, locale);
	}

	@Override
	public String getMessage(MessageSourceResolvable messageSourceResolvable, Locale locale) {
		return this.messageSource.getMessage(messageSourceResolvable, locale);
	}

	@Override
	public Map<Locale, String> getAllMessages(String key, Locale[] locales, Object... args) {
		return this.getAllMessages(new DefaultMessageSourceResolvable(new String[] { key }, args, key), locales);
	}

	@Override
	public Map<Locale, String> getAllMessages(MessageSourceResolvable messageSourceResolvable, Locale[] locales) {
		Map<Locale, String> result = new HashMap<>();
		for (Locale locale : locales) {
			result.putIfAbsent(locale, this.getMessage(messageSourceResolvable, locale));
		}
		return result;
	}

}
