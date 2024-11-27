package com.calerts.computer_alertsbe.authservice.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.util.Collections;

@Configuration
public class JwtConfig {

    @Value("${auth0.domain}")
    private String auth0Domain;

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("https://" + auth0Domain + "/.well-known/jwks.json").build();
    }


//    @Bean
//    public JwtAuthenticationConverter customJwtAuthenticationConverter() {
//        return new JwtAuthenticationConverter() {
//            @Override
//            protected AbstractAuthenticationToken convert(Jwt jwt) {
//                // Custom implementation to convert JWT to Authentication object
//                // For simplicity, this example returns a UsernamePasswordAuthenticationToken
//                String username = jwt.getSubject();
//                return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
//            }
//        };
//    }
}
