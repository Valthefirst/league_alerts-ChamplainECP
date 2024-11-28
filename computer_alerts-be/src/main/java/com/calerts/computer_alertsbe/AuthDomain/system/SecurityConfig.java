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
import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${frontend.url}")
    private String frontendDomain;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/create"))
                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/api/v1/readers").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/create")).permitAll()

                )
         .cors(httpSecurityCorsConfigurer -> {
            final var cors = new CorsConfiguration();
            cors.setAllowedOrigins(List.of(frontendDomain));
            cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE, OPTIONS, PUT"));
            cors.setAllowedHeaders(Arrays.asList("authorization", "content-type", "xsrf-token"));
            cors.setExposedHeaders(List.of("xsrf-token"));
            cors.setAllowCredentials(true);
            cors.setMaxAge(3600L);
        });

        return http.build();
//                .addFilterBefore(new JwtAuthenticationFilter(), BasicAuthenticationFilter.class);

    }

}
