package com.reglus.academy.validator;


public class EmailValidator {
    public static boolean isValidEmail(String email) {
        return email.contains("@") && email.contains("."); // Simples verificação de e-mail
    }
}
