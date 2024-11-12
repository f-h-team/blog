package com.chao.config;

import com.chao.Filter.JwtSecurityFilter;
import com.chao.utils.Md5Encoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @Resource
    JwtSecurityFilter jwtSecurityFilter;

    @Bean
    public Md5Encoding md5Encoding() {
        return new Md5Encoding();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http
                 .csrf().disable()
                 .authorizeRequests().antMatchers("/user/login").permitAll()
                .antMatchers("/user/register").permitAll()
                .anyRequest().authenticated();
         http.addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
