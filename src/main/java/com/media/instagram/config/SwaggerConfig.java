package com.media.instagram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
 * ISSUE : https://yonguri.tistory.com/87
 */
@Profile("develop")
@Configuration
@EnableSwagger2
public class SwaggerConfig{

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.instagramApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.media.instagram.controller"))    // 보여줄 패키지 지정
                .paths(PathSelectors.ant("/**"))             // 보여줄 api 패스 설정 가능
                .build();
    }

    private ApiInfo instagramApiInfo(){
        return new ApiInfoBuilder()
                .title("Clone Instagram Document")
                .description("비공식 팀명 시기와 질투 팀의 인서타그램 기능 클론")
                .build();
    }
}
