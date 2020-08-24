package com.whiz.app.boot.infrastructure.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket userApi() {
        List<Parameter> params = new ArrayList<Parameter>();
        ParameterBuilder tokenParam = new ParameterBuilder();
        tokenParam.name("Authorization")
            .description("token (+Bearer)")
            .modelRef(new ModelRef("string"))
            .parameterType("header")
            .required(true)
            .build();
        params.add(tokenParam.build());
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.whiz.app.boot.application.controller"))
            .paths(PathSelectors.ant("/api/**"))
            .build()
            .globalOperationParameters(params)
            .groupName("com.whiz")
            .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Swagger2 for Station Finder")
            .contact(new Contact("JITAO", "https://github.com/skymarlio82", "jitao.liu82@gmail.com"))
            .version("0.1.0")
            .build();
    }
}