package com.reglus.academy.service;

import com.reglus.academy.exception.*;
import com.reglus.academy.model.Usuario;
import com.reglus.academy.repository.UsuarioRepositorio;

public class UsuarioService {
    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        //Validação do email
        if (!isValidEmail(usuario.getEmail())) {
            throw new EmailInvalidoException("Erro. Email inválido!");
        }

        // Validação do nome completo
        if (usuario.getNomeCompleto() == null || usuario.getNomeCompleto().trim().isEmpty()) {
            throw new NomeInvalidoException("Erro. Campo 'Nome completo' não foi inserido!");
        }

        // Verificação do campo "Login"
        if (usuario.getLogin() == null || usuario.getLogin().trim().isEmpty()) {
            throw new LoginInvalidoException("Erro. Campo 'Login' não foi inserido!");
        }

        // Validação da senha
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            throw new SenhaInvalidaException("Erro. Campo 'Senha' não foi inserido!");
        } else if (usuario.getSenha().length() < 8) {
            throw new SenhaInvalidaException("Erro. A senha deve ter pelo menos 8 dígitos.");
        }

        // Verificação de login duplicado
        if (usuarioRepositorio.findByLogin(usuario.getLogin()) != null) {
            throw new LoginDuplicadoException("Erro. Uma conta já foi criada com esse login!");
        }

        //salva no repositorii
        return usuarioRepositorio.save(usuario);
    }

    // Método auxiliar para validar e-mail
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains("."); // Simples verificação de e-mail
    }
}
