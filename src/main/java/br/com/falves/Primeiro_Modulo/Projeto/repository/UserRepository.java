/**
 * @author falvesmac
 */

package br.com.falves.Primeiro_Modulo.Projeto.repository;

import br.com.falves.Primeiro_Modulo.Projeto.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final List<Usuario> usuarios;

    public UserRepository() {
        this.usuarios = new ArrayList<>();
    }

    public void cadastrarUsuario(Usuario user){
        usuarios.add(user);
    }

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarios.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst().orElse(null);
    }
}