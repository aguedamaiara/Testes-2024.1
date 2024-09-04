package com.reglus.academy.repository;

import com.reglus.academy.model.Usuario;

public interface UsuarioRepositorio {
    Usuario save(Usuario usuario);
    // Novo método adicionado para encontrar um usuário pelo login

    Usuario findByLogin(String login);
}
