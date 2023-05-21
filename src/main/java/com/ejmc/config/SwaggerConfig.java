package com.ejmc.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "공통 코드 API Document",
                version = "v0.0.1",
                description = "공통 코드 API 명세서입니다."
        )
)
@Configuration
public class SwaggerConfig {

}
