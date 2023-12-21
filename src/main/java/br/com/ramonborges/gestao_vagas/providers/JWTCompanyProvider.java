package br.com.ramonborges.gestao_vagas.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JWTCompanyProvider {

    @Value("${security.token.secret}")
    private String secretKey;

    public String validateToken(String token){
        token = token.replace("Bearer ", "");

        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

        try {
            var subject = JWT.require(algorithm)
                           .build()
                           .verify(token)
                           .getSubject();
            return subject;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return "";
        }
    }

}
