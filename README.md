# Identidade, Segurança Web e Autenticação

Bem-vindo à Seção 5! Na etapa anterior (Web Fundamentals), você construiu o motor da sua aplicação: uma API capaz de receber requisições, processar dados e salvar no banco. No entanto, nós construímos uma casa com todas as portas destrancadas. Qualquer usuário com o link do seu servidor pode cadastrar ou deletar produtos da sua loja.

## Veja o repositório deste conteúdo

[GitHub - rafalves106/Java_WebSecurity](https://github.com/rafalves106/Java_WebSecurity)

No mundo real, as APIs precisam saber **quem** está fazendo a requisição (Autenticação) e se essa pessoa tem **permissão** para fazer aquilo (Autorização).

Nesta seção, vamos elevar o seu nível para o de um desenvolvedor Pleno, implementando as mesmas práticas de segurança usadas por gigantes da tecnologia.

### 🗺️ O Mapa da Jornada

Nesta seção, passaremos pelos seguintes módulos:

- **Módulo 1: Proteção de Dados e Hashing (BCrypt):** Como nunca salvar senhas em texto puro e proteger os dados do usuário.
- **Módulo 2: O Padrão JWT (JSON Web Token):** A evolução das "sessões". Como criar o "crachá digital" que acompanha o usuário a cada requisição.
- **Módulo 3: Filtros e Interceptadores (Middlewares):** Como criar "guarda-costas" no seu código que bloqueiam requisições não autorizadas antes mesmo delas chegarem ao seu Controller.
- **Módulo 4: Infraestrutura Básica (O Mundo Real):** Como servidores reais funcionam, portas, IPs e o caminho para colocar sua API na nuvem.

---

## 🏆 Projeto Épico da Seção: Unfiltered API V2 (O Painel Administrativo Seguro)

**Objetivo:** Integrar o seu sistema de catálogo de roupas (desenvolvido na Seção 4) com um sistema de autenticação profissional. Você vai criar áreas públicas (para clientes) e áreas restritas (para administradores).

### 📝 O Cenário

A marca *Unfiltered* cresceu. Agora, você precisa de um painel de controle onde apenas funcionários autorizados possam gerenciar o estoque. O aplicativo do cliente final pode ver as roupas, mas de jeito nenhum pode apagá-las.

### 🚀 Requisitos Técnicos (A Integração Total)

**1. Domínio de Usuários e Segurança:**

- Criar a entidade `Usuario` (id, nome, email, senha).
- A senha **nunca** deve ser salva ou trafegada em texto puro no banco de dados (deve usar BCrypt).

**2. Rotas de Autenticação (Abertas):**

- `POST /api/auth/registrar`: Recebe JSON com dados do administrador, gera o hash da senha e salva no banco. (Status 201).
- `POST /api/auth/login`: Recebe email e senha. O sistema valida contra o banco de dados. Se correto, gera e retorna um **Token JWT** (Status 200). Se errado, retorna erro de credenciais (Status 401).

**3. O Catálogo de Produtos (A API Antiga Evoluída):**

- **Rotas Públicas:** `GET /api/produtos` (Qualquer pessoa pode acessar para ver o catálogo, não exige token).
- **Rotas Privadas:** `POST /api/produtos` e `DELETE /api/produtos`.
    - *Regra de Ouro:* Essas rotas **exigem** que o cliente envie o header `Authorization: Bearer <seu_token_jwt>` na requisição.

**4. O Interceptador (O Guarda-Costas):**

- Você deverá criar um sistema que verifica todas as requisições que chegam nas rotas privadas. Se não houver Token, ou se o Token for inválido/expirado, o servidor deve barrar a requisição imediatamente retornando **401 Unauthorized** ou **403 Forbidden**, sem nem acionar o `EstoqueHandler`.
