package net.chrisgrollier.cloud.apps.sample.contract.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration.
 */
@Configuration
@EnableSwagger2
public class ApplicationConfig {

    @Bean
    public Docket api() {
        //@formatter:off
        return new Docket(DocumentationType.SWAGGER_2)
        	.select()
                  .apis(RequestHandlerSelectors
                		  .basePackage("com.inetpsa.dvp.socle.common.sample.contract"))
                  .paths(PathSelectors.any())
                  .build()
                  .pathMapping("/")
                  .enableUrlTemplating(true);
        //@formatter:on

    }

}
