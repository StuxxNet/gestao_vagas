package br.com.ramonborges.gestao_vagas.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.ramonborges.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.ramonborges.gestao_vagas.modules.company.dto.AuthCompanyRequestDTO;
import br.com.ramonborges.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.ramonborges.gestao_vagas.modules.company.repository.CompanyRepository;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public AuthCompanyResponseDTO execute(AuthCompanyRequestDTO authCompanyRequestDTO) throws AuthenticationException {
        var company = companyRepository.findByName(authCompanyRequestDTO.getName()).orElseThrow(
            () -> {
                throw new CompanyNotFoundException();
            }
        );
        
        if(!this.passwordEncoder.matches(authCompanyRequestDTO.getPassword(), company.getPassword())) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("javagas")
            .withExpiresAt(expiresIn)
            .withSubject(company.getId().toString())
            .sign(algorithm);

        var autoCompanyResponse = AuthCompanyResponseDTO.builder()
            .access_token(token)
            .expires_in(expiresIn.toEpochMilli())
            .build();

        return autoCompanyResponse;
    }

}
