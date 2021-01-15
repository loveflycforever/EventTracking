package com.apoem.mmxx.eventtracking.infrastructure.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: SwaggerConfig </p>
 * <p>Description: Swagger2 配置 </p>
 * <p>Date: 2020/7/16 12:27 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("微店接口文档")
                .description("微店 Api 管理")
                .version("1.0.0")
                .build();
    }
    @Bean
    public Docket createRestApi() {
        List<Parameter> parameters = new ArrayList<>();
        Parameter viaTimestamp = new ParameterBuilder().name("viaTimestamp").description("请求时间（yyyy-MM-dd HH:mm:ss.SSS）")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        Parameter signWrinkles = new ParameterBuilder().name("signWrinkles").description("签名内容（HmacSha256）")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        Parameter sourceSystem = new ParameterBuilder().name("sourceSystem").description("系统来源")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        parameters.add(viaTimestamp);
        parameters.add(signWrinkles);
        parameters.add(sourceSystem);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalOperationParameters(parameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.apoem.mmxx.eventtracking.interfaces.facade"))
                .paths(PathSelectors.any())
                .build();
    }
}