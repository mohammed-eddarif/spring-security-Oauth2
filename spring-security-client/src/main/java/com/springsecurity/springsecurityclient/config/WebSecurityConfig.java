package com.springsecurity.springsecurityclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] WHITE_LIST = {
            "/hello",
            "/register",
            "verifyRegistration",
            "resendVerifyToken"
    };
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors((cors) -> cors.disable())
                .csrf((csrf) -> csrf.disable())
                .securityMatchers((matchers) -> matchers
                        .requestMatchers(WHITE_LIST)
                )
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().anonymous()
                );

        return http.build();
    }
}
