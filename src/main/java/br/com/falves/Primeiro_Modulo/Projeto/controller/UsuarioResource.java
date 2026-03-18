/**
 * @author falvesmac
 */

package br.com.falves.Primeiro_Modulo.Projeto.controller;

import br.com.falves.Primeiro_Modulo.Projeto.dto.Login;
import br.com.falves.Primeiro_Modulo.Projeto.model.Usuario;
import br.com.falves.Primeiro_Modulo.Projeto.service.UserService;
import br.com.falves.Primeiro_Modulo.Projeto.util.Mensagem;
import br.com.falves.Primeiro_Modulo.Projeto.util.Resposta;
import br.com.falves.Primeiro_Modulo.Projeto.util.SenhaUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class UsuarioResource implements HttpHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserService service;

    public UsuarioResource(UserService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        String metodo = exchange.getRequestMethod();
        String caminho = exchange.getRequestURI().getPath();

        int codigo = 404;
        String json = "{\"status\":\"ERRO\", \"mensagem\":\"Rota não encontrada\"}";
        Mensagem mensagem;

        try {
            if ("POST".equalsIgnoreCase(metodo)){
                if (caminho.endsWith("/registro")) {
                    Usuario user = objectMapper.readValue(exchange.getRequestBody(), Usuario.class);
                    try {
                        service.cadastrarUsuario(user);
                        codigo = 201;
                        mensagem = new Mensagem(Resposta.SUCESSO, "Usuário cadastrado com sucesso.");
                        json = objectMapper.writeValueAsString(mensagem);
                    } catch (IllegalArgumentException e) {
                        codigo = 400;
                        mensagem = new Mensagem(Resposta.ERRO, e.getMessage());
                        json = objectMapper.writeValueAsString(mensagem);
                    }
                }

                if (caminho.endsWith("/login")){
                    try {
                        Login loginInfo = objectMapper.readValue(exchange.getRequestBody(), Login.class);

                        Usuario userEncontrado = service.buscarUsuarioPorEmail(loginInfo.getEmail());

                        boolean valida = SenhaUtil.validarSenha(loginInfo.getSenha(), userEncontrado.getSenha());

                        if (valida){
                            mensagem = new Mensagem(Resposta.SUCESSO, "Login realizado com sucesso.");
                            codigo = 200;
                            json = objectMapper.writeValueAsString(mensagem);
                        } else {
                            codigo = 401;
                            mensagem = new Mensagem(Resposta.ERRO, "Credenciais inválidas.");
                            json = objectMapper.writeValueAsString(mensagem);
                        }
                    } catch (Exception e) {
                        codigo = 404;
                        mensagem = new Mensagem(Resposta.ERRO, "Erro: " + e.getMessage());
                        json = objectMapper.writeValueAsString(mensagem);
                    }
                }
            }
        } catch (Exception e){
            codigo = 500;
            mensagem = new Mensagem(Resposta.ERRO, "Erro interno: " + e.getMessage());
            json = objectMapper.writeValueAsString(mensagem);
        }

        byte[] responseBytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(codigo, responseBytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}