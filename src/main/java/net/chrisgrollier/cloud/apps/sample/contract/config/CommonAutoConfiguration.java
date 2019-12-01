package net.chrisgrollier.cloud.apps.sample.contract.config;

import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.chrisgrollier.cloud.apps.common.i18n.MessageManager;
import net.chrisgrollier.cloud.apps.common.internal.i18n.DefaultMessageManager;

/**
 * Auto configuration class responsible to create a
 * {@link DefaultMessageManager} beans if not present in the application
 * context.
 */
@Configuration
public class CommonAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public MessageManager messageManager(MessageSource messageSource) {
		LoggerFactory.getLogger(CommonAutoConfiguration.class).info("autoConfService",
				"instanciating DefaultMessageManager");
		return new DefaultMessageManager(messageSource);
	}

}
