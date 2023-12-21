package br.com.ramonborges.gestao_vagas.modules.company.controller;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ramonborges.gestao_vagas.modules.company.dto.AuthCompanyRequestDTO;
import br.com.ramonborges.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;

@RestController
@RequestMapping("/auth")
public class AuthCompanyController {

    @Autowired
    private AuthCompanyUseCase authCompanyUseCase;
    
    @PostMapping("/company")
    public ResponseEntity<Object> auth(@RequestBody AuthCompanyRequestDTO authCompanyRequestDTO) {
        try {
            var token = this.authCompanyUseCase.execute(authCompanyRequestDTO);
            return ResponseEntity.ok().body(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
