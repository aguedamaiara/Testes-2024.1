package com.reglus.academy.service;

import com.reglus.academy.exceptions.*;
import com.reglus.academy.model.Usuario;
import com.reglus.academy.repository.UsuarioRepositorio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import com.reglus.academy.exceptions.EmailInvalidoException;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("MA-TC_001 - Realizar cadastro de usuário com sucesso")
    public void testCadastroUsuarioComSucesso() {
        // Dados do teste
        Usuario usuario = new Usuario("maiarasantos", "123456@f", "Maiara Águeda da Costa Santos", "05/02/2003", "test@gmail.com");

        // Quando salvar, retorna o mesmo usuário (mockando o comportamento do repositório)
        when(usuarioRepositorio.save(any(Usuario.class))).thenReturn(usuario);

        // Chamar o método que será testado
        Usuario usuarioSalvo = usuarioService.cadastrarUsuario(usuario);

        // Verificar se o cadastro foi realizado com sucesso
        assertNotNull(usuarioSalvo);
        assertEquals("maiarasantos", usuarioSalvo.getLogin());
        assertEquals("123456@f", usuarioSalvo.getSenha());
        assertEquals("Maiara Águeda da Costa Santos", usuarioSalvo.getNomeCompleto());
        assertEquals("05/02/2003", usuarioSalvo.getDataNascimento());
        assertEquals("test@gmail.com", usuarioSalvo.getEmail());

        // Verificar se o método save foi chamado uma vez
        verify(usuarioRepositorio, times(1)).save(usuario);
    }

    @Test
    @DisplayName("MA-TC_002 - Realizar cadastro de usuário com email inválido")
    public void testCadastroUsuarioComEmailInvalido() {
        // Dados do teste com e-mail inválido
        Usuario usuario = new Usuario("maiarasantos", "123456@f", "Maiara Águeda da Costa Santos", "05/02/2003", "teste");

        // Espera-se que o serviço lance uma exceção ao tentar cadastrar um usuário com e-mail inválido
        Exception exception = assertThrows(EmailInvalidoException.class, () -> {
            usuarioService.cadastrarUsuario(usuario);
        });

        // Verificar a mensagem da exceção
        assertEquals("Erro. Email inválido!", exception.getMessage());

        // Verificar que o método save não foi chamado
        verify(usuarioRepositorio, times(0)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("MA-TC_003 -Realizar cadastro de usuário com login já usado")
    public void testCadastroUsuarioComLoginDuplicado() {
        // Dados do teste com login duplicado
        Usuario usuarioExistente = new Usuario("maiarasantos", "senhaExistente", "Nome Existente", "01/01/2000", "existente@gmail.com");
        Usuario usuarioNovo = new Usuario("maiarasantos", "123456@f", "Maiara Águeda da Costa Santos", "05/02/2003", "test@gmail.com");

        // Simular que o login já existe no repositório
        when(usuarioRepositorio.findByLogin("maiarasantos")).thenReturn(usuarioExistente);

        // Espera-se que o serviço lance uma exceção ao tentar cadastrar um usuário com login duplicado
        Exception exception = assertThrows(LoginDuplicadoException.class, () -> {
            usuarioService.cadastrarUsuario(usuarioNovo);
        });

        // Verificar a mensagem da exceção
        assertEquals("Erro. Uma conta já foi criada com esse login!", exception.getMessage());

        // Verificar que o método save não foi chamado
        verify(usuarioRepositorio, times(0)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("MA-TC_004 -Realizar cadastro de usuário com senha com menos de 8 digitos")
    public void testCadastroUsuarioComSenhaInvalida() {
        // Dados do teste com senha inválida
        Usuario usuario = new Usuario("maiarasantos", "123456", "Maiara Águeda da Costa Santos", "05/02/2003", "test@gmail.com");

        // Espera-se que o serviço lance uma exceção ao tentar cadastrar um usuário com senha inválida
        Exception exception = assertThrows(SenhaInvalidaException.class, () -> {
            usuarioService.cadastrarUsuario(usuario);
        });

        // Verificar a mensagem da exceção
        assertEquals("Erro. A senha deve ter pelo menos 8 dígitos.", exception.getMessage());

        // Verificar que o método save não foi chamado
        verify(usuarioRepositorio, times(0)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("MA-TC_005 - Realizar cadastro de usuário sem campo 'Nome completo'")
    public void testCadastroUsuarioSemNomeCompleto() {
        // Dados do teste com nome completo inválido (campo vazio)
        Usuario usuario = new Usuario("maiarasantos", "123456@f", "  ", "05/02/2003", "test@gmail.com");

        // Espera-se que o serviço lance uma exceção ao tentar cadastrar um usuário sem nome completo
        Exception exception = assertThrows(NomeInvalidoException.class, () -> {
            usuarioService.cadastrarUsuario(usuario);
        });

        // Verificar a mensagem da exceção
        assertEquals("Erro. Campo 'Nome completo' não foi inserido!", exception.getMessage());

        // Verificar que o método save não foi chamado
        verify(usuarioRepositorio, times(0)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("MA-TC_006 - Realizar cadastro de usuário sem campo 'Login'")
    public void testCadastroUsuarioSemLogin() {
        // Dados do teste com campo "Login" vazio
        Usuario usuario = new Usuario(" ", "123456@f", "Maiara Águeda da Costa Santos", "05/02/2003", "test@gmail.com");

        // Espera-se que o serviço lance uma exceção ao tentar cadastrar um usuário sem login
        Exception exception = assertThrows(LoginInvalidoException.class, () -> {
            usuarioService.cadastrarUsuario(usuario);
        });

        // Verificar a mensagem da exceção
        assertEquals("Erro. Campo 'Login' não foi inserido!", exception.getMessage());

        // Verificar que o método save não foi chamado
        verify(usuarioRepositorio, times(0)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("MA-TC_007 - Realizar cadastro de usuário sem campo 'Senha'")
    public void testCadastroUsuarioSemSenha() {
        // Dados do teste com campo "Senha" vazio
        Usuario usuario = new Usuario("maiarasantos", " ", "Maiara Águeda da Costa Santos", "05/02/2003", "test@gmail.com");

        // Espera-se que o serviço lance uma exceção ao tentar cadastrar um usuário sem senha
        Exception exception = assertThrows(SenhaInvalidaException.class, () -> {
            usuarioService.cadastrarUsuario(usuario);
        });

        // Verificar a mensagem da exceção
        assertEquals("Erro. Campo 'Senha' não foi inserido!", exception.getMessage());

        // Verificar que o método save não foi chamado
        verify(usuarioRepositorio, times(0)).save(any(Usuario.class));
    }



}