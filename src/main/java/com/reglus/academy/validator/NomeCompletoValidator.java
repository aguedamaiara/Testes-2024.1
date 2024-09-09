package com.reglus.academy.validator;

import com.reglus.academy.exceptions.NomeInvalidoException;

public class NomeCompletoValidator {
    public static void validate(String nomeCompleto) {
        if (nomeCompleto == null || nomeCompleto.trim().isEmpty()) {
            throw new NomeInvalidoException("Erro. Campo 'Nome completo' não foi inserido!");
        }
    }
}
