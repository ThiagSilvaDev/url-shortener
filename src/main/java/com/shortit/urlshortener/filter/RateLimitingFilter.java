package com.shortit.urlshortener.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimitingFilter implements Filter {

    private static final int MAX_REQUESTS_PER_MINUTE = 5;
    private final Map<String, AtomicInteger> requestCountPerIpAddress = new ConcurrentHashMap<>();


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String clientIp = request.getRemoteAddr();

        requestCountPerIpAddress.putIfAbsent(clientIp, new AtomicInteger(0));
        AtomicInteger requestCount = requestCountPerIpAddress.get(clientIp);

        int requests = requestCount.incrementAndGet();

        if (requests > MAX_REQUESTS_PER_MINUTE) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Too many requests\"}");
            response.getWriter().flush();
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
