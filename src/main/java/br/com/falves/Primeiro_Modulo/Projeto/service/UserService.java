/**
 * @author falvesmac
 */

package br.com.falves.Primeiro_Modulo.Projeto.service;

import br.com.falves.Primeiro_Modulo.Projeto.repository.UserRepository;
import br.com.falves.Primeiro_Modulo.Projeto.model.Usuario;

public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public boolean cadastrarUsuario(Usuario user){
        if (user == null){
            throw new IllegalArgumentException("Usuário é nulo e não pode ser cadastrado.");
        }

        if (user.getEmail().isEmpty() || user.getSenha().isEmpty()){
            throw new IllegalArgumentException("Email e senha do usuário não podem estar vazios.");
        }

        if (!user.getEmail().contains("@") || !user.getEmail().contains(".com")){
            throw new IllegalArgumentException("Insira um e-mail válido.");
        }

        repo.cadastrarUsuario(user);
        return true;
    }

    public Usuario buscarUsuarioPorEmail(String email){
        if (email == null){
            throw new IllegalArgumentException("Usuário é nulo e não pode ser cadastrado.");
        }

        if (email.isEmpty()){
            throw new IllegalArgumentException("Email e senha do usuário não podem estar vazios.");
        }

        if (!email.contains("@")) {
            throw new IllegalArgumentException("Insira um e-mail válido.");
        }

        Usuario user = repo.buscarUsuarioPorEmail(email);

        if (user == null){
            throw new IllegalArgumentException("Insira um e-mail válido.");
        }

        return user;
    }

}