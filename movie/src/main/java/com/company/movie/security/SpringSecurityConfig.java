package com.company.movie.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.company.movie.security.filter.JwtAuthenticationFilter;

@Configuration
public class SpringSecurityConfig {

    @Bean
    /**
     * @Qualifier("jpaUserDetailService")
     * 
     * En Laravel, esto sería equivalente a inyectar una dependencia específica mediante
     * el contenedor de servicios (Service Container).
     * 
     * Como podrías tener varios "UserProviders" (por ejemplo, uno para Admins, otro para Usuarios),
     * @Qualifier le dice a Spring: "No me des cualquier UserDetailsService, 
     * inyéctame específicamente la implementación que tiene este nombre (el ID del bean)."
     */
    AuthenticationManager authenticationManager(@Qualifier("jpaUserDetailService") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    @Order(1)
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        return http
            .securityMatcher("/api/**")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users", "/api/login").permitAll()
                .anyRequest().authenticated()
            )
            .addFilter(new JwtAuthenticationFilter(authenticationManager))
            .csrf(config -> config.disable())
            .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }
}
