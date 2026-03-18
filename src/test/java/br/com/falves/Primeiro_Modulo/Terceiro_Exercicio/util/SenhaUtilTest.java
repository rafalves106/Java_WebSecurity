package br.com.falves.Primeiro_Modulo.Terceiro_Exercicio.util;

import br.com.falves.Primeiro_Modulo.Terceiro_Exercicio.model.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SenhaUtilTest {

    private Usuario user;

    @BeforeEach
    void setup() {
        user = new Usuario(1L, "Rafael", "rafalves106@icloud.com", "TesteSenha");
    }

    @Test
    void deveValidarSenhaCorreta(){
        String hash = user.getSenha();
        boolean valida = SenhaUtil.validarSenha("TesteSenha", hash);

        Assertions.assertTrue(valida);
    }

    @Test
    void deveInvalidarSenhaIncorreta(){
        String hash = user.getSenha();
        boolean valida = SenhaUtil.validarSenha("TesteErrado", hash);

        Assertions.assertFalse(valida);
    }

    @Test
    void deveVerificarSeASenhaPassouPeloHash(){
        String hash = user.getSenha();

        Assertions.assertNotEquals("TesteSenha", hash);
    }
}