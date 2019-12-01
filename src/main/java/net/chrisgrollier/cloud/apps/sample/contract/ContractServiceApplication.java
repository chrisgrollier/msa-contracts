package net.chrisgrollier.cloud.apps.sample.contract;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

import net.chrisgrollier.cloud.apps.common.log.aop.support.DefaultLoggableAspect;

/**
 * This class is the Application Class.
 */
@SpringBootApplication
@RefreshScope
public class ContractServiceApplication {

    /**
     * This is the most important method who calls the simulation.
     * 
     * @param args args passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(ContractServiceApplication.class, args);
    }
	
    // for calling userService
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
	
    // for i18n support in validator
	@Bean
	public LocalValidatorFactoryBean getValidator(MessageSource messageSource) {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource);
		return bean;
	}

	// for auto logging generation
	@Bean
	public DefaultLoggableAspect loggableAspect() {
		return new DefaultLoggableAspect();
	}

}
