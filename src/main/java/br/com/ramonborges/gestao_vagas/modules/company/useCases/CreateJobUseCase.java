package br.com.ramonborges.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ramonborges.gestao_vagas.modules.company.entities.JobEntity;
import br.com.ramonborges.gestao_vagas.modules.company.repository.JobRepository;

@Service
public class CreateJobUseCase {

    @Autowired
    JobRepository jobRepository;
    
    public JobEntity execute(JobEntity jobEntity) {
        return this.jobRepository.save(jobEntity);
    }

}
