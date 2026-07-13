package com.company.movie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    /**
     * Define la cadena de filtros de seguridad (Middleware Stack).
     * 
     * @Order(2) actúa como un "Middleware Priority" o orden de ejecución.
     * Si tienes varios SecurityFilterChain, este se ejecutará después del que tenga @Order(1).
     */
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
        // Se encarga de TODO lo que no fue API
        .securityMatcher("/**")
        .authorizeHttpRequests(auth -> auth
 // Rutas públicas
                 .requestMatchers("/*", "/moviesDetails/*", "/movieByVendor/**", 
                 "/search/**", "/css/**", "/js/**", "/img/**", "/images/**",
                  "/login", "/logout").permitAll()
 
                 
                 // Rutas que requieren autenticación
              
                 .requestMatchers("/movies/edit/**").hasAnyRole("ADMIN", "EDITOR")
                 .requestMatchers("/movies/delete/**").hasRole("ADMIN")
                 .requestMatchers("/admin/**").hasRole("ADMIN")
                 .requestMatchers("/genres/**").hasRole("ADMIN")
                 
                 .anyRequest().authenticated()
        ).formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/", true))
            .logout(config -> config.logoutSuccessUrl("/"))
            .build()
        
        ;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
