package br.com.ramonborges.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ramonborges.gestao_vagas.exceptions.CompanyFoundException;
import br.com.ramonborges.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.ramonborges.gestao_vagas.modules.company.repository.CompanyRepository;

@Service
public class CreateCompanyUseCase {
    
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity companyEntity){
        this.companyRepository
            .findByNameOrEmail(companyEntity.getName(), companyEntity.getEmail())
            .ifPresent(name -> {
                throw new CompanyFoundException();
            });
        
        var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);

        return this.companyRepository.save(companyEntity);
    }

}
