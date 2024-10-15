## Desafio Xperian

### Objetivo 
 
Controle de estoque de uma editora.

**Regras**

- Cada livro deve estar associado a um Autor(a), mas nem todo Autor(a) precisa ter um livro;


**Tecnologias**

- Java 17;
- Maven 3.9.9
- Banco de dados em memória(H2);
- Spring 3;
- Swagger;
- Actuator;

**Iniciar aplicação**

`` ./build.sh``

**Monitoramento e mapeamento**

``http://localhost:8085/actuator/health``
``http://localhost:8085/swagger-ui/index.htm``


**Melhorias futuras**

- Integrar com sistema de autenticação externa(Keycloak);
- Integrar com banco de dados(Postgres);
- Dockerizar a aplicação;
- Usar variáveis de ambiente;
- Manipular dados dos usuários;.