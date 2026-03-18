/**
 * @author falvesmac
 */

package br.com.falves.Primeiro_Modulo.Segundo_Exercicio;

import br.com.falves.Primeiro_Modulo.Segundo_Exercicio.model.Usuario;
import br.com.falves.Primeiro_Modulo.Segundo_Exercicio.util.SenhaUtil;

public class Main {
    public static void main(String[] args) {
        Usuario user = new Usuario(1L, "Falves", "rafalves106@icloud.com", "TesteSenha");

        String hash = user.getSenha();

        boolean valida = SenhaUtil.validarSenha("TesteSenha", hash);

        if (valida){
            System.out.println("Senha válida.");
        } else {
            System.out.println("Senha inválida.");
        }
    }

}