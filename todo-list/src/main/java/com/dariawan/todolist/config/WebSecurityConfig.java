package com.dariawan.todolist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
           .mvcMatchers(HttpMethod.GET, "/api/**", "/now", "/").permitAll()
        .anyRequest().denyAll();
    }
    
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {     
//        auth.inMemoryAuthentication()
//                .withUser("barista").password("{noop}espresso").roles("USER");
//    }
}
