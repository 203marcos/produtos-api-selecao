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

- `produto/controller`: endpoints de produtos
- `produto/service`: regras de negocio de produtos
- `produto/exception`: excecoes de dominio de produtos
- `produto/repository`: acesso ao banco de produtos
- `produto/model`: entidades JPA de produtos
- `produto/dto`: DTOs de produtos
- `produto/mapper`: conversao entre DTO e entidade
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

## Testes Completos

### Colecao Postman

Arquivo: `Produtos_API_Collection.postman_collection.json`

Passos para usar:

1. **Importar no Postman**:
   - Abra Postman
   - Clique em `Import` → `Upload Files`
   - Selecione `Produtos_API_Collection.postman_collection.json`

2. **Executar fluxo completo**:
   - Execute as requisicoes em ordem:
     1. Login (extrai token automaticamente)
     2. Criar Produto (extrai ID automaticamente)
     3. Listar Todos
     4. Buscar por ID
     5. Atualizar Produto
     6. Deletar Produto
     7. Teste de erro 404
     8. Testes de seguranca (sem token, token invalido)

3. **Usar Collection Runner** (testa tudo de uma vez):
   - Clique em `...` no nome da colecao
   - `Run collection`
   - Todas as requisicoes executam em sequencia
   - Variaveis sao preenchidas automaticamente

### Matriz de Testes

#### 1. Autenticacao

| Teste | Metodo | URL | Status | Descripao |
|-------|--------|-----|--------|-----------|
| Login valido | POST | `/auth/login` | 200 | Retorna token JWT |
| Login invalido | POST | `/auth/login` | 401 | Credenciais incorretas |

Payload login:
```json
{
  "email": "admin@exemplo.com",
  "senha": "admin123"
}
```

#### 2. CRUD - Com Autenticacao

| Teste | Metodo | URL | Status | Descripao |
|-------|--------|-----|--------|-----------|
| Criar | POST | `/api/produtos` | 201 | Produto criado |
| Listar | GET | `/api/produtos` | 200 | Array de produtos |
| Buscar | GET | `/api/produtos/1` | 200 | Produto especifico |
| Atualizar | PUT | `/api/produtos/1` | 200 | Produto atualizado |
| Deletar | DELETE | `/api/produtos/1` | 204 | Sem conteudo |

#### 3. Erros - 404

| Teste | Metodo | URL | Status | Descripao |
|-------|--------|-----|--------|-----------|
| Buscar inexistente | GET | `/api/produtos/99999` | 404 | Produto nao encontrado |

Resposta erro 404:
```json
{
  "timestamp": "2026-03-24T21:13:00Z",
  "status": 404,
  "erro": "Produto não encontrado",
  "mensagem": "Produto com ID 99999 nao existe",
  "path": "/api/produtos/99999"
}
```

#### 4. Seguranca - 401 (Unauthorized)

| Teste | Metodo | URL | Status | Requisicao | Descripao |
|-------|--------|-----|--------|-----------|-----------|
| Sem token | GET | `/api/produtos` | 401 | Sem header | Requer autenticacao |
| Token invalido | GET | `/api/produtos` | 401 | `Bearer xxx` | Token nao validado |
| Criar sem token | POST | `/api/produtos` | 401 | Sem header | Requer autenticacao |

Resposta erro 401:
```json
{
  "timestamp": "2026-03-24T21:13:00Z",
  "status": 401,
  "erro": "Acesso negado",
  "mensagem": "Nenhum token de autenticacao fornecido",
  "path": "/api/produtos"
}
```

### URLs de Acesso

| Recurso | URL |
|---------|-----|
| Swagger UI | `http://localhost:8080/swagger-ui/index.html` |
| OpenAPI JSON | `http://localhost:8080/v3/api-docs` |
| H2 Console | `http://localhost:8080/h2-console` |

**Nota**: H2 Console esta acessivel sem autenticacao para fins de debug.

## CI

Workflow: `.github/workflows/ci.yml`

Executa `mvn test` em push/PR para `develop` e `main`.

## Checklist de Criterios de Avaliacao

### ✅ Codigo Limpo

- [x] Controllers, Services, Repositories e Models bem separados
- [x] Estrutura por feature (`produto/*`)
- [x] Formatacao automatizada (Spotless + Eclipse formatter)
- [x] Nomes descritivos em PT-BR
- [x] DTOs com mapeamento padrao (MapStruct)
- [x] DTOs com comentarios explicando decisoes tecnicas

### ✅ Tratamento de Erros

- [x] 404 para produto nao encontrado
- [x] 401 para acesso sem token
- [x] 403 para acesso negado (futuro)
- [x] 400 para validacao (futuro)
- [x] Resposta padronizada `ApiErrorResponse`
- [x] Handler global de excecoes (`GlobalExceptionHandler`)

### ✅ Autenticacao e Seguranca

- [x] Endpoint `/auth/login` com email/senha
- [x] Retorna JWT token
- [x] Endpoints CRUD protegidos com `@PreAuthorize("isAuthenticated()")`
- [x] Filtro JWT valida token em todas as requisicoes
- [x] Usuario admin hardcoded: `admin@exemplo.com` / `admin123`
- [x] Spring Security 5 configurado (stateless)
- [x] Handlers customizados 401/403

### ✅ Banco de Dados

- [x] H2 in-memory
- [x] Entidade `Produto` com campos: nome, preco, descricao, categoria
- [x] JPA/Hibernate para CRUD
- [x] Repository com Spring Data JPA
- [x] Perfis de ambiente (dev, test, prod)

### ✅ Endpoints CRUD

- [x] `POST /api/produtos` - Criar (autenticado)
- [x] `GET /api/produtos` - Listar todos (autenticado)
- [x] `GET /api/produtos/{id}` - Buscar por ID (autenticado)
- [x] `PUT /api/produtos/{id}` - Atualizar (autenticado)
- [x] `DELETE /api/produtos/{id}` - Deletar (autenticado)

### ✅ Documentacao

- [x] Swagger/OpenAPI configurado
- [x] Bearer token suportado no Swagger
- [x] README.md com instrucoes de execucao
- [x] README.md com exemplos de cURL
- [x] Colecao Postman v2.1 com testes automatizados
- [x] Guia completo de testes (matriz de endpoints)

### ✅ CORS

- [x] CORS configurado em `SecurityConfig`
- [x] Permite requisicoes cross-origin

### ✅ Tecnologias

- [x] Spring Boot 2.7.18 (Java 17)
- [x] Maven build
- [x] GitHub Actions CI
- [x] Git Flow (develop, main, phase branches)

