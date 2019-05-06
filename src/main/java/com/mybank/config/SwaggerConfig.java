package com.mybank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
	
	
	private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<String>(Arrays.asList("application/json",  "application/xml"));
	
	@Bean
    public Docket mybankApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mybank"))
              //  .paths(regex("/Account"))
                .build()
                .apiInfo(metaInfo())
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "MyBank Account API",
                "MyBank Account API",
                "1.0",
                "Terms of Service",
                new Contact("MyBank", "https://mybank.com",
                        "mybankc@mybank.com"),
                "blah License Version 2.0",
                "blah",
                Collections.emptyList()
        );

        return apiInfo;
    }

}
