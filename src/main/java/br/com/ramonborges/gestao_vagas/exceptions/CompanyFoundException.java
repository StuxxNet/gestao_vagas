package br.com.ramonborges.gestao_vagas.exceptions;

public class CompanyFoundException extends RuntimeException {
    public CompanyFoundException(){
        super("The company already exists in the database");
    }
}
