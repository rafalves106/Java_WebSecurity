/**
 * @author falvesmac
 */

package br.com.falves.Primeiro_Modulo.Terceiro_Exercicio.model;

import br.com.falves.Primeiro_Modulo.Terceiro_Exercicio.util.SenhaUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String senha;

    public void setSenha(String senha) {
        this.senha = SenhaUtil.hashearSenha(senha);
    }

    public Usuario(Long id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        setSenha(senha);
    }
}