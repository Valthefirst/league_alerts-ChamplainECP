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
                        .pathMatchers(HttpMethod.GET, "/api/v1/readers/**").permitAll()
                                .pathMatchers(HttpMethod.PATCH, "/api/v1/readers/**").permitAll()
                                .pathMatchers(HttpMethod.PUT, "/api/v1/readers/**").permitAll()
                        .pathMatchers(HttpMethod.GET,"/api/v1/articles/**").permitAll()
                                .pathMatchers(HttpMethod.PATCH, "/api/v1/articles/**").permitAll()

                                .pathMatchers(HttpMethod.GET, "/api/v1/interactions/likes/**").hasAuthority("like:articles")
                                .pathMatchers(HttpMethod.POST, "/api/v1/interactions/like/**").hasAuthority("like:articles")
                                .pathMatchers(HttpMethod.DELETE, "/api/v1/interactions/unlike/**").hasAuthority("like:articles")

                                .pathMatchers(HttpMethod.GET, "/api/v1/interactions/saves/**").hasAuthority("save:articles")
                                .pathMatchers(HttpMethod.POST, "/api/v1/interactions/saves").hasAuthority("save:articles")
                                .pathMatchers(HttpMethod.DELETE, "/api/v1/interactions/saves/**").hasAuthority("save:articles")


                                .pathMatchers(HttpMethod.GET, "/api/v1/likes/**").hasAuthority("like:articles")
                                .pathMatchers(HttpMethod.DELETE, "/api/v1/likes/**").hasAuthority("like:articles")




                        //---------------AuthorEndpoints
                        .pathMatchers(HttpMethod.POST, "/api/v1/articles/**").hasAuthority("create:articles")

                        .pathMatchers(HttpMethod.GET, "/api/v1/authors/**").permitAll()

                        //.pathMatchers(HttpMethod.PUT, "/api/v1/articles/**").hasAuthority("create:articles")
                                .pathMatchers(HttpMethod.PUT, "/api/v1/articles/**").permitAll()

                                .pathMatchers(HttpMethod.PATCH, "/api/v1/articles/acceptArticle/").hasAuthority("admin:articles")


                        //--------------AdminEndpoints
                                .pathMatchers(HttpMethod.POST, "api/create/Author").hasAuthority("admin:articles")
                                .pathMatchers(HttpMethod.POST, "api/create/Reader").permitAll()
                                .pathMatchers(HttpMethod.POST, "api/create/**").permitAll()

                                .pathMatchers(HttpMethod.PUT, "/api/update/**").authenticated()
                        .pathMatchers(HttpMethod.DELETE, "/api/delete/**").authenticated()

                        .pathMatchers(HttpMethod.PUT, "/api/v1/articles/**").authenticated()
//                        .pathMatchers(HttpMethod.POST, "/api/rules/").permitAll()
//                        .pathMatchers(HttpMethod.OPTIONS, "/api/rules/").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/v1/interactions/**").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/v1/interactions/**").permitAll()
                        .pathMatchers(HttpMethod.DELETE, "/api/v1/interactions/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/v1/likes/**").permitAll()
                        .pathMatchers(HttpMethod.DELETE, "/api/v1/likes/**").permitAll()
                        .pathMatchers(HttpMethod.PUT, "/api/v1/articles/**").permitAll()
                                .pathMatchers(HttpMethod.GET, "/api/v1/send-email/**").permitAll()

                                .pathMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                                .pathMatchers(HttpMethod.POST, "/api/v1/categories/**").permitAll()

                                .pathMatchers(HttpMethod.GET, "/api/v1/tags/**").permitAll()

                                .pathMatchers(HttpMethod.POST, "/api/v1/tags/**").permitAll()



                                .pathMatchers(HttpMethod.GET, "/api/v1/subscriptions/**").permitAll()
                                .pathMatchers(HttpMethod.POST, "/api/v1/subscriptions/**").permitAll()
                                .pathMatchers(HttpMethod.DELETE, "/api/v1/subscriptions/**").permitAll()
                                .pathMatchers(HttpMethod.GET, "api/v1/reports/**").hasAuthority("admin:articles")

                        // Catch-all to require authentication for other endpoints
                        .anyExchange().authenticated()
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
            JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
            // Set to "permissions" to match your token structure
            authoritiesConverter.setAuthoritiesClaimName("permissions");
            // Remove the authority prefix since your permissions don't have one
            authoritiesConverter.setAuthorityPrefix("");

            Collection<GrantedAuthority> authorities = authoritiesConverter.convert(jwt);

            // Debug logging to see what's happening
            System.out.println("Permissions from token: " + jwt.getClaim("permissions"));
            authorities.forEach(auth ->
                    System.out.println("Converted authority: " + auth.getAuthority())
            );

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