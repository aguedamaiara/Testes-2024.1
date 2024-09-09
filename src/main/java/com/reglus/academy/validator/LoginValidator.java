package com.reglus.academy.validator;

import com.reglus.academy.exceptions.LoginInvalidoException;

public class LoginValidator {
    public static void validate(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new LoginInvalidoException("Erro. Campo 'Login' não foi inserido!");
        }
    }
}
