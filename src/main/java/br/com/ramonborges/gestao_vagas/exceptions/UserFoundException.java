package br.com.ramonborges.gestao_vagas.exceptions;

public class UserFoundException extends RuntimeException{
    public UserFoundException() {
        super("The user already exists!");
    }
}
