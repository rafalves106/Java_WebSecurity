/**
 * @author falvesmac
 */

package br.com.falves.Primeiro_Modulo.Projeto.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mensagem {
    Resposta resposta;
    String texto;

    public Mensagem(Resposta resposta, String texto) {
        this.resposta = resposta;
        this.texto = texto;
    }
}