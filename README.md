# Cadastro de Chaves Pix

Este projeto é uma API desenvolvida em **Spring Boot** para gerenciar chaves Pix, permitindo operações de cadastro, alteração, inativação e consulta de chaves Pix. O banco de dados utilizado é o **PostgreSQL**.

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- JUnit & Mockito (para testes)
- Postman (para testes de API)

## Funcionalidades

- **Incluir chave Pix**: Cadastro de novas chaves Pix com validações específicas.
- **Atualizar chave Pix**: Permite atualização de dados da chave, exceto tipo e valor.
- **Inativar chave Pix**: Desativa uma chave já cadastrada.
- **Consultar chaves Pix**: Busca chaves pelo ID, tipo de chave, agência/conta e outros filtros combináveis.

## Como Executar o Projeto

1. Clone o repositório:
   ```sh
   git clone https://github.com/lucas-castione/cadastro-chave-pix.git
   ```
2. Configure o banco de dados PostgreSQL.
3. Ajuste o arquivo `application.properties` ou as variáveis de ambiente para conexão ao banco.
4. Compile e execute o projeto:
   ```sh
   mvn spring-boot:run
   ```

## Aplicação dos Princípios do 12 Factor

Este projeto segue vários princípios do **12 Factor App**:

1. **Base de Código**: O código é versionado em um repositório Git único, permitindo múltiplas deploys.
2. **Dependências**: As dependências são gerenciadas via Maven, garantindo reprodutibilidade.
3. **Configurações**: Utilizamos variáveis de ambiente para configurar a conexão com o banco de dados, evitando credenciais hardcoded.
4. **Backing Services**: O PostgreSQL é tratado como um recurso externo configurável.
5. **Construção, Release e Execução**: A aplicação segue um fluxo claro de build (`mvn package`), release e execução (`java -jar`).
6. **Processos**: A API é stateless, sendo possível escalar horizontalmente com múltiplas instâncias.
7. **Vinculação de Portas**: A API expõe serviços via HTTP, sem dependências rígidas com servidores específicos.
8. **Concorrência**: A escalabilidade pode ser alcançada rodando múltiplos processos independentes.
9. **Descarte Rápido**: A aplicação inicia rapidamente e responde a SIGTERM corretamente para encerramento controlado.
10. **Paridade de Ambientes**: O uso de variáveis de ambiente facilita a similaridade entre os ambientes de desenvolvimento, homologação e produção.
11. **Logs**: Os logs são emitidos no stdout/stderr, podendo ser gerenciados por ferramentas externas.
12. **Tarefas Administrativas**: Migrações de banco podem ser feitas com scripts específicos rodando como processos one-off.

## Testes

Os testes unitários foram implementados usando **JUnit** e **Mockito**. Para rodá-los, execute:
```sh
mvn test
```

## Autor

Desenvolvido por Lucas Castione

