package org.ioteatime.meonghanyangserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()); // 모든 요청 허용
        http.authorizeHttpRequests((auth)->{
            auth.requestMatchers("/**").permitAll()
                    .anyRequest()
                    .authenticated();
        });
        return http.build();
    }
}