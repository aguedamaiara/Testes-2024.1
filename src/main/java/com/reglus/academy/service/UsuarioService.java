package com.reglus.academy.service;

import com.reglus.academy.model.Usuario;
import com.reglus.academy.repository.UsuarioRepositorio;
import com.reglus.academy.validator.*;
import com.reglus.academy.exceptions.*;

public class UsuarioService {
    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        // Validação do email
        if (!EmailValidator.isValidEmail(usuario.getEmail())) {
            throw new EmailInvalidoException("Erro. Email inválido!");
        }

        NomeCompletoValidator.validate(usuario.getNomeCompleto());

        LoginValidator.validate(usuario.getLogin());

        SenhaValidator.validate(usuario.getSenha());

        if (usuarioRepositorio.findByLogin(usuario.getLogin()) != null) {
            throw new LoginDuplicadoException("Erro. Uma conta já foi criada com esse login!");
        }

        return usuarioRepositorio.save(usuario);
    }
}
