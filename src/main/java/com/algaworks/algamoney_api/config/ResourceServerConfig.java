package com.algaworks.algamoney_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

//@Profile("oauth-security")
@EnableWebSecurity
@Configuration
// Não é necessário anotar @Configuration, uma vez que esta anotação já tem dentro de EnableWebSecurity, mas n tem problema em declarar aqui tbm
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {



    //Revisando aula 6.1
    @Autowired
    public void configure (AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}admin")
                .roles("ROLE");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeHttpRequests().requestMatchers("/*").permitAll()
        .anyRequest().authenticated()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                /**Configuração do OAuth Resource Server
                 * No Spring Security 5 o resource server deve ser
                 * configurado de forma diferente da versão anterior.
                 * Para isso, é utilizado o método oauth2ResourceServer,
                 * em conjunto com o tipo de token a ser utilizado.
                 * Essa configuração de tipo de token é obrigatória*/
                .oauth2ResourceServer().opaqueToken();
        return http.build();
    }

//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.stateless(true);
//    }

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
