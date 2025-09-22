package com.shortit.urlshortener.config;

import com.shortit.urlshortener.filter.RateLimitingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"!test", "!dev"})
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RateLimitingFilter> customRateLimitingFilter() {
        FilterRegistrationBean<RateLimitingFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RateLimitingFilter());
        registration.addUrlPatterns("/shortener");
        return registration;
    }
}
