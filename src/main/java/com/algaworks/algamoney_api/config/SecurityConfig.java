package com.algaworks.algamoney_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

//@Profile("oauth-security")
@EnableWebSecurity
@Configuration
// Não é necessário anotar @Configuration, uma vez que esta anotação já tem dentro de EnableWebSecurity, mas n tem problema em declarar aqui tbm
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeHttpRequests().requestMatchers("/*").permitAll()
        .anyRequest().authenticated()
        .and().cors(Customizer.withDefaults())
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

   @Bean
   public CorsConfigurationSource corsConfigurationSource(){
       CorsConfiguration config = new CorsConfiguration();
       config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
       config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS", "HEAD", "TRACE", "CONNECT"));
       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       source.registerCorsConfiguration("/**", config);
       return source;
   }

    // add o método passwordEncoder

}
