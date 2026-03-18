/**
 * @author falvesmac
 */

package br.com.falves.Primeiro_Modulo.Segundo_Exercicio.util;

import org.mindrot.jbcrypt.BCrypt;

public class SenhaUtil {
    public static String hashearSenha(String senhaPura){
        return BCrypt.hashpw(senhaPura, BCrypt.gensalt());
    }

    public static boolean validarSenha(String senhaPura, String hashSalvo){
        return BCrypt.checkpw(senhaPura, hashSalvo);
    }
}