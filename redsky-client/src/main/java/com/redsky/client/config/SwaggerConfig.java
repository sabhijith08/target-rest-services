package com.redsky.client.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).select()
                .apis(RequestHandlerSelectors.basePackage("com.redsky.client.controller")).build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("REDSKY Service", "REST Service APIs", "V1.0", "Terms of service",
                new Contact(": REDSKY Team", "", ""), "", "", new ArrayList<VendorExtension>());
    }

}
