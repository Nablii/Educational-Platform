package tn.enis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // désactive CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/**").permitAll() // autorise l'accès sans authentification
                        .requestMatchers("/api/topics/**").permitAll() // Temporary
                        .requestMatchers("/api/quiz/**").permitAll() // Temporary
                        .requestMatchers("/api/results/**").permitAll() // Temporary
                        .anyRequest().authenticated() // toutes les autres requêtes nécessitent une auth
                );

        return http.build();
    }
}
