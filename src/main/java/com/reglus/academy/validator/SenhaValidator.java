package com.reglus.academy.validator;

import com.reglus.academy.exceptions.SenhaInvalidaException;

public class SenhaValidator {
    public static void validate(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new SenhaInvalidaException("Erro. Campo 'Senha' não foi inserido!");
        } else if (senha.length() < 8) {
            throw new SenhaInvalidaException("Erro. A senha deve ter pelo menos 8 dígitos.");
        }
    }
}
