# Produtos API

API REST para gerenciamento de produtos com Spring Boot 2, autenticacao JWT e documentacao Swagger.

## Tecnologias

- Java 17
- Spring Boot 2.7.18
- Spring Web
- Spring Data JPA
- Spring Security
- H2 Database
- JWT (jjwt)
- Swagger/OpenAPI (springdoc)
- Maven

## Requisitos atendidos

- CRUD completo de produtos:
  - `POST /api/produtos`
  - `GET /api/produtos`
  - `GET /api/produtos/{id}`
  - `PUT /api/produtos/{id}`
  - `DELETE /api/produtos/{id}`
- Login JWT:
  - `POST /auth/login`
- Usuario admin hardcoded:
  - email: `admin@exemplo.com`
  - senha: `admin123`
- Endpoints CRUD protegidos por autenticacao JWT
- Tratamento de erros padronizado (`404`, `400`, `401`, `403`, `500`)
- H2 em memoria
- Swagger com suporte a Bearer Token

## Estrutura de pastas

- `controller`: endpoints de produtos
- `service`: regras de negocio
- `service/exception`: excecoes de dominio da camada de servico
- `repository`: acesso ao banco
- `model`: entidades JPA
- `dto`: DTOs de produtos
- `auth`: login e DTOs de autenticacao
- `security`: configuracao de seguranca, JWT e handlers 401/403
- `exception/handler`: tratamento global de excecoes
- `exception/payload`: formato padrao de erro da API
- `config`: configuracoes adicionais (OpenAPI)

## Perfis

- `dev` (default): H2 em memoria, SQL habilitado
- `test`: H2 em memoria com `create-drop`
- `prod`: variaveis de ambiente para banco, JWT e CORS

Arquivos:

- `src/main/resources/application.properties`
- `src/main/resources/application-dev.properties`
- `src/main/resources/application-test.properties`
- `src/main/resources/application-prod.properties`

## Como rodar

```bash
mvn spring-boot:run
```

Rodar com perfil especifico:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
mvn spring-boot:run -Dspring-boot.run.profiles=test
```

Executar testes:

```bash
mvn test
```

## Swagger

- URL da UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

Para testar endpoints protegidos:

1. Chame `POST /auth/login` e copie o token.
2. No Swagger, clique em `Authorize`.
3. Informe `Bearer <seu_token>`.

## Exemplos de cURL

Login:

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@exemplo.com","senha":"admin123"}'
```

Criar produto:

```bash
curl -X POST http://localhost:8080/api/produtos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN" \
  -d '{"nome":"Notebook","preco":4500.00,"descricao":"Notebook i7","categoria":"Informatica"}'
```

Listar produtos:

```bash
curl -X GET http://localhost:8080/api/produtos \
  -H "Authorization: Bearer SEU_TOKEN"
```

Buscar por ID:

```bash
curl -X GET http://localhost:8080/api/produtos/1 \
  -H "Authorization: Bearer SEU_TOKEN"
```

Atualizar produto:

```bash
curl -X PUT http://localhost:8080/api/produtos/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN" \
  -d '{"nome":"Notebook Pro","preco":5200.00,"descricao":"Notebook i7 16GB","categoria":"Informatica"}'
```

Deletar produto:

```bash
curl -X DELETE http://localhost:8080/api/produtos/1 \
  -H "Authorization: Bearer SEU_TOKEN"
```

## CI

Workflow: `.github/workflows/ci.yml`

Executa `mvn test` em push/PR para `develop` e `main`.
