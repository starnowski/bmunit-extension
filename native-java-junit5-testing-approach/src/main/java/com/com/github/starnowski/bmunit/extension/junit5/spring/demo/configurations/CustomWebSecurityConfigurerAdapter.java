package com.com.github.starnowski.bmunit.extension.junit5.spring.demo.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    public void configure(WebSecurity web) throws Exception {
        // Disable security
        web
                .ignoring()
                .antMatchers("/**");
    }
}