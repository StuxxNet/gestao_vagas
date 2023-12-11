package br.com.ramonborges.gestao_vagas.modules.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ramonborges.gestao_vagas.exceptions.CompanyFoundException;
import br.com.ramonborges.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.ramonborges.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company")
public class CompanyController {
    
    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity){
        try {
            var result = createCompanyUseCase.execute(companyEntity);
            return ResponseEntity.ok().body(result);
        } catch (CompanyFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
