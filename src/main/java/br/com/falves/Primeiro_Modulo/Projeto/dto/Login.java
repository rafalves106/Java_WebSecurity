/**
 * @author falvesmac
 */

package br.com.falves.Primeiro_Modulo.Projeto.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Login {
    private String email;
    private String senha;
}