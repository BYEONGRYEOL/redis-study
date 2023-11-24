package com.example.hanghaero.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.hanghaero.filter.HttpLoggingFilter;

@Configuration
public class WebSecurityConfig {

	@Bean
	public FilterRegistrationBean<HttpLoggingFilter> httpLoggingFilter() {
		FilterRegistrationBean<HttpLoggingFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new HttpLoggingFilter());
		registrationBean.addUrlPatterns("/**");
		return registrationBean;
	}
}
