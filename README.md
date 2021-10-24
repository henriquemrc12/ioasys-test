# Teste Ioasys API

## Informações gerais

- Usuário Admin Padrão `Email: admin@email.com` `Senha: 12345678`
- NÃO excluir/desativar o usuário padrão, caso não exista outro Admin cadatrado no sistema
- Na rota de cadastro de usuário `POST /users` aceita dois tipos de usuário, `COMMON` e o usuário `ADMIN`. O usuário `ADMIN` somente pode ser cadastrado por outro ADMIN.

## Pré-requisitos
Ter as seguintes tecnologias instaladas
- MySQL
- JDK 8
- Apache Maven 3

Antes de rodar o projeto
- Criar um banco de dados com nome 'ioasys'
- Trocar os atributos `spring.datasource.username` e `spring.datasource.password` para as credenciais do MySQL que está rodando. Trocar no arquivo `/resources/application.properties`
- Trocar os atributos `spring.flyway.user` e `spring.flyway.password` para as credenciais do MySQL que está rodando. Trocar no arquivo `/resources/application.properties`

## Rodar o projeto

Para rodar a API rode os seguinte comandos
```bash
# Faz o Build da API
mvn clean install

#Inicia a API
java -jar target/api-0.0.1-SNAPSHOT.jar
```
## Local que os serviços estarão rodando
- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html