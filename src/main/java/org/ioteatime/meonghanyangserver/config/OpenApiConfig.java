package org.ioteatime.meonghanyangserver.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info =
                @Info(
                        title = "멍하냥 API 명세서",
                        description = "API 명세서",
                        version = "v1",
                        contact = @Contact(name = "서유진", email = "seoyoujin97@gmail.com")))
@Configuration
public class OpenApiConfig {}
