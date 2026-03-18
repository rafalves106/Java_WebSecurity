## A Forja de Criptografia (`SenhaUtil`)

**Objetivo:** Aplicar o Princípio da Responsabilidade Única (SRP). Em vez de espalhar a lógica do BCrypt por todo o seu código, você vai criar uma classe "ferramenta" centralizada que cuidará exclusivamente de blindar e verificar senhas.

### 📝 Passo a Passo:

1. **Preparação:** Garanta que a dependência do `jbcrypt` (versão 0.4) está no seu arquivo `pom.xml`.
2. **Estrutura:** Crie um novo pacote no seu projeto chamado `br.com.falves.Projeto.util` (ou `security`).
3. **A Classe:** Crie uma classe chamada `SenhaUtil`. Como ela será uma classe utilitária, seus métodos devem ser estáticos (assim você não precisa dar `new SenhaUtil()` toda vez que for usar).
4. **Método de Hashing:** * Crie o método `public static String hashearSenha(String senhaPura)`.
    - Dentro dele, use o `BCrypt.hashpw()` junto com `BCrypt.gensalt()` para retornar a senha embaralhada.
5. **Método de Validação:**
    - Crie o método `public static boolean validarSenha(String senhaPura, String hashSalvo)`.
    - Dentro dele, use o `BCrypt.checkpw()` para retornar `true` se as senhas baterem, ou `false` caso contrário.