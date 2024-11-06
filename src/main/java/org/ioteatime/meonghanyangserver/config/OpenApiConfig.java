package org.ioteatime.meonghanyangserver.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info =
                @Info(
                        title = "멍하냥 API 명세서",
                        description = "API 명세서",
                        version = "v1",
                        contact = @Contact(name = "서유진", email = "seoyoujin97@gmail.com")))
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        SecurityScheme apiKey =
                new SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Bearer Token");

        Server productionServer = new Server();
        productionServer.setDescription("Production Server");
        productionServer.setUrl("https://my-server-name.com");

        Server localServer = new Server();
        localServer.setDescription("Local Server");
        localServer.setUrl("http://localhost:8080");

        return new OpenAPI()
                .addSecurityItem(getSecurityRequirement())
                .components(getAuthComponent())
                .servers(List.of(productionServer, localServer))
                .components(new Components().addSecuritySchemes("Bearer Token", apiKey))
                .addSecurityItem(securityRequirement);
    }

    private SecurityRequirement getSecurityRequirement() {
        String jwt = "JWT";
        return new SecurityRequirement().addList(jwt);
    }

    private Components getAuthComponent() {
        return new Components()
                .addSecuritySchemes(
                        "JWT",
                        new SecurityScheme()
                                .name("JWT")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization"));
    }
}
