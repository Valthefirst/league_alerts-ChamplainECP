package com.calerts.computer_alertsbe;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@Component
//public class RequestLoggingFilter implements Filter {
//    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, javax.servlet.ServletException {
//        if (request instanceof HttpServletRequest) {
//            HttpServletRequest httpRequest = (HttpServletRequest) request;
//            logger.info("Incoming Request: {} {}", httpRequest.getMethod(), httpRequest.getRequestURI());
//            httpRequest.getHeaderNames().asIterator().forEachRemaining(headerName ->
//                    logger.info("Header: {} = {}", headerName, httpRequest.getHeader(headerName))
//            );
//        }
//        chain.doFilter(request, response);
//    }
//}

