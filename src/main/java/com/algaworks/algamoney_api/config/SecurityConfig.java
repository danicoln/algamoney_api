package com.algaworks.algamoney_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

//@Profile("oauth-security")
@EnableWebSecurity
@Configuration
// Não é necessário anotar @Configuration, uma vez que esta anotação já tem dentro de EnableWebSecurity, mas n tem problema em declarar aqui tbm
public class SecurityConfig {

    //Tirado da internet (O metodo withDefaultPasswordEncoder está deprecated)
//    public InMemoryUserDetailsManager userDetailsService(){
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("admin")
//                .roles("ROLE")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

    //da aula

    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("admin").password("admin").roles("ROLE");
    //ROLE seria a autorizacao que o usuario deveria ter
    }
//
//   public void configure(HttpSecurity http) throws Exception{
//        http.authorizeHttpRequests()
//                .requestMatchers("/categorias").permitAll()
//                .requestMatchers("/lancamentos").permitAll()
//                .requestMatchers("/pessoas").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic().and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .csrf().disable();
//   }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return  http.csrf().disable()
                .authorizeHttpRequests().requestMatchers("/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // add o método passwordEncoder

}
