# Proteção de Dados, Hashing e o Padrão BCrypt

A regra de ouro número um do desenvolvimento backend moderno é simples e inegociável: **Nunca, sob nenhuma circunstância, armazene senhas em texto puro (plain text) no banco de dados.** Se o seu banco de dados for vazado e as senhas estiverem expostas, os hackers não terão acesso apenas ao seu sistema, mas potencialmente à vida inteira do usuário, já que a maioria das pessoas repete senhas em vários sites e aplicativos. Neste módulo, aprenderemos a transformar senhas legíveis em códigos indecifráveis.

## 1. A Diferença Fundamental: Criptografia vs. Hashing

Muitos desenvolvedores confundem esses dois termos, mas eles têm propósitos completamente diferentes:

- **Criptografia (Encryption):** É uma via de **mão dupla**. Você usa uma chave (ou algoritmo) para embaralhar um texto e pode usar a mesma chave para desembaralhá-lo depois.
    - *Exemplo de uso:* Salvar números de cartão de crédito. O sistema precisa ler o número original na hora de cobrar.
- **Hashing:** É uma via de **mão única**. Você transforma a senha "rafael123" em um código longo de caracteres (ex: `$2a$10$vI8aWBn...`). **É matematicamente impossível reverter esse código de volta para a senha original**.
    - *Exemplo de uso:* Senhas. Nem mesmo você (o dono do banco de dados) deve saber qual é a senha do usuário.

## 2. O Problema das "Rainbow Tables" e a Solução: Salting

Antigamente, usava-se algoritmos simples como MD5 ou SHA-256 para gerar hashes. O problema é que a senha "123456" sempre gerava exatamente o mesmo hash. Hackers então criaram dicionários gigantescos chamados *Rainbow Tables*, contendo milhões de senhas comuns e seus respectivos hashes pré-calculados. Se eles vissem aquele hash no seu banco, já saberiam a senha.

A solução da indústria foi o **Salt (Sal)**. O Salt é uma string aleatória gerada dinamicamente e adicionada à senha *antes*dela passar pelo processo de Hashing. Com isso, se dois usuários tiverem a senha "123456", os hashes gerados no banco serão **completamente diferentes**, inutilizando as *Rainbow Tables*.

## 3. O Padrão Ouro: BCrypt

O **BCrypt** é o algoritmo de hashing mais utilizado na indústria Java (é o motor padrão do Spring Security, inclusive). Ele brilha por dois motivos:

1. Ele já implementa o *Salting* automaticamente (o salt fica embutido no próprio texto do hash final).
2. Ele tem um "Work Factor" (Fator de Custo). Ele é projetado para ser propositalmente "lento" para o processador (demorando cerca de 100 a 300 milissegundos). Isso não afeta um usuário fazendo login, mas torna impossível que um hacker faça um ataque de *Força Bruta* tentando milhões de senhas por segundo.

---

## 4. Implementação Prática (Java Nativo)

Como ainda não estamos usando Spring, vamos importar a biblioteca clássica `jBcrypt` para fazer esse trabalho pesado.

### 📦 Adicionando a Dependência

No seu arquivo `pom.xml` (caso esteja usando Maven), adicione a biblioteca dentro da tag `<dependencies>`:

```xml
<dependency>
    <groupId>org.mindrot</groupId>
    <artifactId>jbcrypt</artifactId>
    <version>0.4</version>
</dependency>
```

### 💻 Como Usar o BCrypt no Código

A biblioteca nos fornece dois métodos essenciais: um para criar o hash no momento do cadastro, e outro para checar a senha no momento do login.

```java
import org.mindrot.jbcrypt.BCrypt;

public class TesteSeguranca {
    public static void main(String[] args) {
        String senhaDigitadaNoCadastro = "senhaForte@2026";

        // 1. GERANDO O HASH (O que vai ser salvo no Banco de Dados)
        // O BCrypt.gensalt() gera o fator aleatório de segurança automaticamente
        String hashParaSalvar = BCrypt.hashpw(senhaDigitadaNoCadastro, BCrypt.gensalt());
        
        System.out.println("Salvo no BD: " + hashParaSalvar); 
        // Exemplo de saída: $2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjGPIA.O5q

        // 2. VERIFICANDO O LOGIN (Quando o usuário tenta entrar no sistema)
        String senhaTentativaLogin = "senhaForte@2026";
        
        // O checkpw faz a mágica: ele extrai o "salt" do hash salvo e verifica se a senha bate
        boolean senhaCorreta = BCrypt.checkpw(senhaTentativaLogin, hashParaSalvar);
        
        if(senhaCorreta) {
            System.out.println("Login Autorizado!");
        } else {
            System.out.println("Senha Inválida.");
        }
    }
}
```