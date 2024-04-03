package br.rockethub.chessbackend.configuration;


import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



import java.util.Collections;


@Configuration
@EnableSwagger2
@SecurityScheme(name = "bearer", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {


    private ApiInfo apiInfo() {
        return new ApiInfo("Spring Boot ", "Spring Boot Doc", "716",
                "Terms of service", new Contact("Test", "www.xyz.com", "test@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

    @Bean
    public Docket actuatorApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Spring Actuator")
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.rockethub.chessbackend.*.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());

    }



}