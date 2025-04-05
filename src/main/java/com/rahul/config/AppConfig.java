package com.rahul.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.security.Security;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Value("${fronted.url}")
    private String frontedUrl;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize-> Authorize
                                .requestMatchers("/api/admin/**").hasAnyRole("RESTAURANT_OWNER", "ADMIN")
                                .requestMatchers("/api/stripe/webhook").permitAll()
                                .requestMatchers("/api/restaurants").permitAll()
                                .requestMatchers("/api/events/home").permitAll()
                                .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf->csrf.disable())
                .cors(cors->cors.configurationSource(corsConfigrationSource()));

        return http.build();
    }

    private CorsConfigurationSource corsConfigrationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg= new CorsConfiguration();
                cfg.setAllowedOrigins(Arrays.asList(
                        "http://localhost:3000/",
                        frontedUrl
                ));


                cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // ✅ No wildcard
                cfg.setAllowCredentials(true); // ✅ Allows cookies, JWT, etc.
                cfg.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // ✅ Specify headers instead of "*"
//                cfg.setAllowedHeaders(Collections.singletonList("*"));
                cfg.setExposedHeaders(Collections.singletonList("Authorization")); // ✅ Allow frontend to access Authorization header
                cfg.setMaxAge(3600L); // Cache the CORS settings for 1 hour
                return cfg;
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
