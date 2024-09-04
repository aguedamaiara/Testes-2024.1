package com.reglus.academy.exception;

public class LoginInvalidoException extends RuntimeException {
    public LoginInvalidoException(String mensagem) {
        super(mensagem);
    }
}