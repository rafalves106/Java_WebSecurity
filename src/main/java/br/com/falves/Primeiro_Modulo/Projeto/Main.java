/**
 * @author falvesmac
 */

package br.com.falves.Primeiro_Modulo.Projeto;

import br.com.falves.Primeiro_Modulo.Projeto.controller.UsuarioResource;
import br.com.falves.Primeiro_Modulo.Projeto.repository.UserRepository;
import br.com.falves.Primeiro_Modulo.Projeto.service.UserService;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        UserRepository repo = new UserRepository();
        UserService service = new UserService(repo);
        UsuarioResource resource = new UsuarioResource(service);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080),0);

        server.createContext("/api/auth", resource);

        server.setExecutor(null);

        server.start();
        System.out.println("Servidor rodando na porta 8080...");
    }
}