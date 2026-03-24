Dando continuidade ao processo seletivo para Desenvolvedor Back-End, gostaríamos que você realizasse um teste técnico.

O objetivo é avaliar sua forma de organização, lógica e aplicação prática das tecnologias.

Peço, por gentileza, que envie a entrega até amanhã às 14h.

Qualquer pergunta, fico à disposição.





Você deve criar uma API REST básica com Spring Boot para gerenciar uma lista de produtos (como nome, preço, descrição e categoria).
- Inclua um CRUD completo (Create, Read, Update, Delete) para os produtos.Requisitos principais:
- Use Spring Boot (versão 2) como framework principal.
- Implemente autenticação JWT com Spring Security:

- Endpoint de login (/auth/login) que recebe email/senha e retorna um token JWT.
- Proteja todos os endpoints CRUD com @PreAuthorize ou filtros JWT (apenas usuários autenticados podem acessar).
- Crie um usuário admin hardcoded ou via banco (email: admin@exemplo.com, senha: admin123).

Banco de dados:
- Use um banco in-memory como H2 (configure via application.properties).
- Crie a entidade Produto com os campos mencionados e use JPA/Hibernate para o CRUD.

Endpoints esperados:

- POST /api/produtos - Criar produto (autenticado).
- GET /api/produtos - Listar todos.
- GET /api/produtos/{id} - Buscar por ID.
- PUT /api/produtos/{id} - Atualizar.
- DELETE /api/produtos/{id} - Deletar.

- Documente a API com Swagger (adicione dependência e configure).
- Teste com Postman ou similar (inclua prints ou curls nos comentários do código).

Critérios de avaliação:
Código limpo, com controllers, services, repositories e models bem separados.
Tratamento de erros (ex: 404 para produto não encontrado, 401/403 para auth).
Configuração correta de CORS, se necessário.
README.md com instruções para rodar (mvn spring-boot:run) e testar.