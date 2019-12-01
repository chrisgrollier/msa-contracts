package net.chrisgrollier.cloud.apps.sample.contract.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * A simple security configuration with csrf disabled for local testing purpose
 * and using http basic authentication.
 * 
 * @author Atos
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.csrf().disable()
			.cors().and()
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.httpBasic();
		// @formatter:on
	}
}
