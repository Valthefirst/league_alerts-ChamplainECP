package com.calerts.computer_alertsbe.authservice.system;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseHeaderInfo implements Filter {

    @Value("${frontend.url}")
    private String frontendDomain;

    @Override
    public void doFilter(
            final ServletRequest request,
            final ServletResponse response,
            final FilterChain chain
    ) throws IOException, ServletException {
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Set CORS headers
        httpResponse.setHeader("Access-Control-Allow-Origin", frontendDomain);
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me, X-XSRF-TOKEN, Authorization");  // Add Authorization header
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE, PATCH");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");

        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            // Handle preflight request
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Continue with the filter chain
        chain.doFilter(request, response);
    }
}
