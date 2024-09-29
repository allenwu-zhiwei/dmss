package com.nusiss.dmss.config;

/*import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;*/

/*@Configuration
@EnableWebSecurity*/
public class SecurityConfig   {

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**").permitAll()  // Permit access to Swagger UI and docs
                        .anyRequest().authenticated()                   // All other endpoints require authentication
                )
                .httpBasic(withDefaults())  // Enable basic authentication with defaults
                .csrf(csrf -> csrf
                        .requireCsrfProtectionMatcher(request -> true) // Apply CSRF protection based on the matcher
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        // Create a default user
        manager.createUser(User.withDefaultPasswordEncoder()
                .username("team13")
                .password("password123")
                .roles("USER")
                .build());
        return manager;
    }*/
}
