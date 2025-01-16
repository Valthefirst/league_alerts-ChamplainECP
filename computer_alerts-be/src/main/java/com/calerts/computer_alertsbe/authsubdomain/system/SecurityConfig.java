package com.calerts.computer_alertsbe.authsubdomain.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
//import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
//import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.oauth2.core.authorization.OAuth2ReactiveAuthorizationManagers.hasScope;

@Configuration
@EnableWebFluxSecurity
@Profile("!test")
public class SecurityConfig {

    @Value("${frontend.url}")
    private String frontendDomain;

    @Value("${auth0.domain}")
    private String AUTH0_DOMAIN;


    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeExchange(exchange -> exchange
                        // Completely public endpoints




                        //---------------ReaderEndpoints
                        .pathMatchers(HttpMethod.GET, "/api/v1/readers/**").permitAll()
                        .pathMatchers(HttpMethod.GET,"/api/v1/articles/**").permitAll()
                                .pathMatchers(HttpMethod.PATCH, "/api/v1/articles/**").permitAll()

                                .pathMatchers(HttpMethod.GET, "/api/v1/interactions/**").authenticated()
                                .pathMatchers(HttpMethod.POST, "/api/v1/interactions/**").authenticated()
                                .pathMatchers(HttpMethod.DELETE, "/api/v1/interactions/**").authenticated()
                                .pathMatchers(HttpMethod.POST, "/api/v1/interactions/**").authenticated()


                                .pathMatchers(HttpMethod.GET, "/api/v1/likes/**").authenticated()
                                .pathMatchers(HttpMethod.DELETE, "/api/v1/likes/**").authenticated()




                        //---------------AuthorEndpoints
                        .pathMatchers(HttpMethod.POST, "/api/v1/articles/**").authenticated()

                        .pathMatchers(HttpMethod.GET, "/api/v1/authors/**").permitAll()

                        .pathMatchers(HttpMethod.PUT, "/api/v1/articles/**").authenticated()

                                .pathMatchers(HttpMethod.PATCH, "/api/v1/articles/acceptArticle/").authenticated()


                        //--------------AdminEndpoints
                                .pathMatchers(HttpMethod.POST, "api/create/Author").authenticated()
                                .pathMatchers(HttpMethod.POST, "api/create/Reader").authenticated()

                                .pathMatchers(HttpMethod.PUT, "/api/update/**").authenticated()
                        .pathMatchers(HttpMethod.DELETE, "/api/delete/**").authenticated()

                        .pathMatchers(HttpMethod.PUT, "/api/v1/articles/**").authenticated()
//                        .pathMatchers(HttpMethod.POST, "/api/rules/").permitAll()
//                        .pathMatchers(HttpMethod.OPTIONS, "/api/rules/").permitAll()

                        // Catch-all to require authentication for other endpoints
                        .anyExchange().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .build();
    }


    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return ReactiveJwtDecoders.fromIssuerLocation("https://dev-im24qkb6l7t2yhha.ca.auth0.com/");
    }






    private Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        return jwt -> {
            // Extract authorities from the "permissions" or "roles" claim
            JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
            authoritiesConverter.setAuthoritiesClaimName("permissions"); // Use "roles" if needed
            authoritiesConverter.setAuthorityPrefix(""); // No prefix

            Collection<GrantedAuthority> authorities = authoritiesConverter.convert(jwt);

            authorities.forEach(auth ->
                    System.out.println("User permission: " + auth.getAuthority())
            );

            // Create and return the authentication token in a reactive way
            return Mono.just(new JwtAuthenticationToken(jwt, authorities));
        };
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontendDomain));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
