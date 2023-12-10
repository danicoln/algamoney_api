package com.algaworks.algamoney_api.infra.security;

import com.algaworks.algamoney_api.domain.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")                     // passamos quem é o emissor
                    .withSubject(usuario.getUsername())         // adicionamos o subject no token
                    .withExpiresAt(generateExpirationDate())    // gera um tempo de expiração
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException ex){
            throw new RuntimeException("Error while generating token", ex);
        }
    }

    /**Método que valida o token para verificar se está válido*/
    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret); // cria o algorithm
            return JWT.require(algorithm)                   // informamos o algorithn criado
                    .withIssuer("auth-api")                 // passamos quem é o emissor
                    .build()                                // montamos o dado dentro de algorithm
                    .verify(token)                          // descriptografamos o token
                    .getSubject();                          // pegamos o subject que tínhamos salvo

        } catch (JWTVerificationException ex){              //  Capturamos a exeção caso, o token for inválido.
            return "";
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now()
                .plusHours(2) //adicionamos uma qtde de horas
                .toInstant(ZoneOffset.of("-3:00")); // inserimos o timezone
    }
}
