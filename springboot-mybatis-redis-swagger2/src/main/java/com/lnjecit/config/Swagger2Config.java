package com.lnjecit.config;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger2Config
 *
 * @author lnj
 * @create 2018-03-04 15:11
 **/
@Configuration
public class Swagger2Config {

    // swagger是否开启 true:开启 false:不开启
    @Value("${swagger.enable}")
    private String enable;

    @Bean
    public Docket createRestApi() {
        Predicate<String> predicate;
        if ("true".equals(enable)) {
            predicate = PathSelectors.any();
         } else {
            predicate = PathSelectors.none();
        }
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lnjecit"))
                .paths(predicate)
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("springboot利用swagger构建api文档")
                .description("springboot整合mybatis、redis、swagger2、shiro")
                .termsOfServiceUrl("http://www.cnblogs.com/zuidongfeng/")
                .version("1.0")
                .build();
    }
}
