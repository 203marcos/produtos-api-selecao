# Produtos API

API REST para cadastro de produtos com Spring Boot 2.

## Stack

- Java 17
- Spring Boot 2.7.18
- Spring Web
- Spring Data JPA
- Spring Security
- H2 Database
- Maven

## Estrategia de branches

- `main`: branch de release
- `develop`: branch de integracao
- `fase/03-dtos-mapeamento`: branch de implementacao da fase atual

Fluxo aplicado:

1. Criar branch de fase a partir de `develop`
2. Desenvolver e validar localmente
3. Merge em `develop`
4. Merge de `develop` em `main`

## Perfis

Foram criados perfis para separar ambientes:

- `dev` (default): H2 em memoria, SQL habilitado
- `test`: H2 em memoria com `create-drop`
- `prod`: configuracao por variaveis de ambiente (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`)

Arquivos:

- `src/main/resources/application.properties`
- `src/main/resources/application-dev.properties`
- `src/main/resources/application-test.properties`
- `src/main/resources/application-prod.properties`

## CI (GitHub Actions)

Workflow: `.github/workflows/ci.yml`

Executa `mvn test` em push/PR para `develop` e `main` (e push em `fase/**`).

## Fase 3 entregue: DTOs e mapeamento

Estrutura implementada:

- `model`: `Produto`
- `dto`: `ProdutoRequestDTO`, `ProdutoResponseDTO`
- `mapper`: `ProdutoMapper`
- `repository`: `ProdutoRepository`
- `service`: `ProdutoService`
- `controller`: `ProdutoController`
- `exception`: `ResourceNotFoundException`, `GlobalExceptionHandler`

Contrato do CRUD:

- Requisicoes usam `ProdutoRequestDTO`
- Respostas usam `ProdutoResponseDTO`
- Entidade `Produto` fica interna na camada de dominio/persistencia

Endpoints CRUD:

- `POST /api/produtos`
- `GET /api/produtos`
- `GET /api/produtos/{id}`
- `PUT /api/produtos/{id}`
- `DELETE /api/produtos/{id}`

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

## Proximas fases

- Fase 4: autenticacao JWT e protecao dos endpoints
- Fase 5: testes de controller/service e refinamentos finais
