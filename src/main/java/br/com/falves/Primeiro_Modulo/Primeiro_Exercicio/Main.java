/**
 * @author falvesmac
 */

package br.com.falves.Primeiro_Modulo.Primeiro_Exercicio;

import br.com.falves.Primeiro_Modulo.Primeiro_Exercicio.util.SenhaUtil;

public class Main {
    public static void main(String[] args) {
        String senha = "TesteSenha";
        String hash = SenhaUtil.hashearSenha(senha);
        boolean valida = SenhaUtil.validarSenha(senha, hash);
        boolean valida2 = SenhaUtil.validarSenha("Teste2", hash);

        if (valida){
            System.out.println("Senha válida.");
        } else {
            System.out.println("Senha inválida");
        }

        if (valida2){
            System.out.println("Senha válida.");
        } else {
            System.out.println("Senha inválida");
        }
    }
}