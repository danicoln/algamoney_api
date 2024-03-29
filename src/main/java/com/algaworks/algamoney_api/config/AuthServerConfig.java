package com.algaworks.algamoney_api.config;

import com.algaworks.algamoney_api.config.property.AlgamoneyApiProperty;
import com.algaworks.algamoney_api.infra.security.UsuarioSistema;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwsEncoder;
import org.springframework.security.oauth2.server.authorization.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Configuration
@Profile("oauth-security")
public class AuthServerConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AlgamoneyApiProperty algamoneyApiProperty;

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        //Cliente do oauth2
        RegisteredClient angularClient = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId("angular")
                .clientSecret(passwordEncoder.encode("@ngul@r0"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUris(uris -> uris.addAll(algamoneyApiProperty.getSeguranca().getRedirectsPermitidos()))
                .scope("read")
                .scope("write")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(1))
                        .refreshTokenTimeToLive(Duration.ofDays(24))
                        .build())
                .clientSettings(
                        ClientSettings.builder()
                                .requireAuthorizationConsent(true) //ativa e desativa a tela de consentimendo do oauth2
                                .build())
                .build();

        RegisteredClient mobileClient = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId("mobile")
                .clientSecret(passwordEncoder.encode("m0b1le"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUris(uris -> uris.addAll(algamoneyApiProperty.getSeguranca().getRedirectsPermitidos()))
                .scope("read")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(1))
                        .refreshTokenTimeToLive(Duration.ofDays(24))
                        .build())
                .clientSettings(
                        ClientSettings.builder()
                                .requireAuthorizationConsent(false) //ativa e desativa a tela de consentimendo do oauth2
                                .build())
                .build();

        return new InMemoryRegisteredClientRepository(
                Arrays.asList(
                        angularClient,
                        mobileClient
                )
        );
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE) //Como existem vários filtros de segurança, este precisa passar primeiro
    public SecurityFilterChain authServerFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        /**
         * no return, passamos a configuração de formulário de login
         * com o Customizer.withDefaults, passamos as configurações padrões,
         * sem ter a necessidade de implementar estas configurações*/
        return http.formLogin(Customizer.withDefaults()).build();
    }

    /**
     * Customização do JWT
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return (context) -> {
            UsernamePasswordAuthenticationToken authenticationToken = context.getPrincipal();
            UsuarioSistema usuarioSistema = (UsuarioSistema) authenticationToken.getPrincipal();

            Set<String> authorities = new HashSet<>();
            for (GrantedAuthority grantedAuthority : usuarioSistema.getAuthorities()) {
                authorities.add(grantedAuthority.getAuthority());
            }
            context.getClaims().claim("nome", usuarioSistema.getUsuario().getNome());
            context.getClaims().claim("authorities", authorities);
        };
    }

    /**
     * Bean que configura as chaves da assinatura do token
     */
    @Bean
    public JWKSet jwtSet() throws JOSEException {
        RSAKey rsa = new RSAKeyGenerator(2048) //tamanha da chave
                .keyUse(KeyUse.SIGNATURE)//assinatura
                .keyID(UUID.randomUUID().toString()) // cria um id randomico
                .generate();

        return new JWKSet(rsa);
    }

    /**
     * Bean que lê a chave de assinatura que estamos utilizando
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource(JWKSet jwkSet) {
        return ((jwkSelector, securityContext) -> jwkSelector.select(jwkSet));
    }

    /**
     * Bean de encoder do jwt
     */
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwsEncoder(jwkSource);
    }

    /**
     * Bean que representa uma parta da assinatura do token, o criador do token*/
    @Bean
    public ProviderSettings providerSettings(){
        return ProviderSettings.builder()
                .issuer(algamoneyApiProperty.getSeguranca().getAuthServerUrl()) // na issuer, podemos passar a url fixa (http:// ..)

                .build();
    }
}
