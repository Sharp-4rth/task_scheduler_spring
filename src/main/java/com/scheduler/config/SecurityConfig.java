package com.scheduler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())       // diables csrf
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()    // Anyone can access login/register endpoints
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())                         // Everything else requires a config
                .build();
    }

    // Tells spring how to find users and passwords
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User
                .withUsername("Leonidas")
                .password("{noop}password")                                   // No password hashing
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);                          // Unrelated to my DB
    }
}
