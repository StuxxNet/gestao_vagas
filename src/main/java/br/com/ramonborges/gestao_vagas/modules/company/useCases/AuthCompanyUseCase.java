package br.com.ramonborges.gestao_vagas.modules.company.useCases;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.ramonborges.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.ramonborges.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.ramonborges.gestao_vagas.modules.company.repository.CompanyRepository;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company = companyRepository.findByName(authCompanyDTO.getName()).orElseThrow(
            () -> {
                throw new CompanyNotFoundException();
            }
        );
        
        if(!this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword())) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

        var token = JWT.create().withIssuer("javagas")
            .withSubject(company.getId().toString())
            .sign(algorithm);

        return token;
    }

}
