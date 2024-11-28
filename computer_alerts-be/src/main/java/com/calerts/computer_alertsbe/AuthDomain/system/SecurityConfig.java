package com.calerts.computer_alertsbe.AuthDomain.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;


import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${frontend.url}")
    private String frontendDomain;

    @Value("${auth0.domain}")
    private String AUTH0_DOMAIN;

    @Value("${auth0.audience}")
    private String AUDIENCE;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .disable() // Disable CSRF protection (you can re-enable if needed)
                )
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/v1/readers").permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/readers")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/create")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.OPTIONS, "/api/create")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "https://dev-im24qkb6l7t2yhha.ca.auth0.com/oauth/token")).permitAll()
                        .anyRequest().authenticated()  // Secure other routes
                )
                .cors(httpSecurityCorsConfigurer -> {
                    final var cors = new CorsConfiguration();
                    cors.setAllowedOrigins(List.of(frontendDomain));
                    cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    cors.setAllowedHeaders(Arrays.asList("authorization", "content-type", "xsrf-token"));
                    cors.setExposedHeaders(List.of("xsrf-token"));
                    cors.setAllowCredentials(true);
                    cors.setMaxAge(3600L);
                })
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.decoder(jwtDecoder())) // Use a custom JwtDecoder
                );

        return http.build();
    }


    ///Maybe Configure for M2M protocol.

    @Bean
    public JwtDecoder jwtDecoder() {
        // The Auth0 issuer URL is typically in the form: https://<YOUR_AUTH0_DOMAIN>/.well-known/jwks.json
        String issuerUri = "https://" + AUTH0_DOMAIN + "/.well-known/jwks.json";

        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(issuerUri).build();

//        // Optionally, configure authorities (roles) for JWT claims
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        JwtAuthoritiesConverter authoritiesConverter = new JwtAuthoritiesConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
//
//        jwtDecoder.setJwtAuthenticationConverter(jwtAuthenticationConverter);

        return jwtDecoder;
    }
}
