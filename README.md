# 🛒 Course API - Web Service RESTful com Spring Boot

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-brightgreen?style=for-the-badge&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Docker-blue?style=for-the-badge&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker)
![H2 Database](https://img.shields.io/badge/Database-H2%20(Test)-blue?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apachemaven)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow?style=for-the-badge)

API RESTful desenvolvida com **Java** e **Spring Boot**, focada nas boas práticas de desenvolvimento backend, arquitetura em camadas, mapeamento objeto-relacional com JPA/Hibernate, validações de entrada, tratamento robusto de exceções e persistência em banco relacional **PostgreSQL** conteinerizado com **Docker Compose**.

> [!IMPORTANT]
> **Status do Projeto: Em Desenvolvimento 🚧**
> O projeto já conta com o modelo de domínio completo, DTOs, validações, tratamento de erros, suporte a múltiplos perfis de ambiente (`dev` com PostgreSQL em Docker e `test` com H2 em memória). Novas funcionalidades como migrações com Flyway, paginação e autenticação JWT estão em desenvolvimento. Veja a seção [Roadmap e Próximos Passos](#-roadmap-e-próximos-passos).

---

## 📌 Sumário

- [O que é o projeto](#-o-que-é-o-projeto)
- [Status e Observação](#-status-e-observação)
- [Funcionalidades e Recursos](#-funcionalidades-e-recursos)
- [Modelo de Domínio](#-modelo-de-domínio)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Requisitos](#-requisitos)
- [Ambientes e Perfis de Execução (Profiles)](#-ambientes-e-perfis-de-execução-profiles)
- [Como Rodar o Projeto](#-como-rodar-o-projeto)
  - [1. Clonar o Repositório](#1-clonar-o-repositório)
  - [2. Subir o Banco de Dados com Docker Compose](#2-subir-o-banco-de-dados-com-docker-compose)
  - [3. Escolher o Perfil de Execução](#3-escolher-o-perfil-de-execução)
  - [4. Executar via Maven Wrapper](#4-executar-via-maven-wrapper)
  - [5. Executar o Pacote JAR](#5-executar-o-pacote-jar)
  - [6. Executar os Testes Automatizados](#6-executar-os-testes-automatizados)
- [Consultando os Bancos de Dados](#-consultando-os-bancos-de-dados)
  - [PostgreSQL no Docker (Perfil `dev`)](#postgresql-no-docker-perfil-dev)
  - [Console Web H2 (Perfil `test`)](#console-web-h2-perfil-test)
- [Endpoints da API](#-endpoints-da-api)
- [Estrutura de Camadas](#-estrutura-de-camadas)
- [Tratamento de Exceções](#-tratamento-de-exceções)
- [Roadmap e Próximos Passos](#-roadmap-e-próximos-passos)

---

## 📖 O que é o projeto

O **Course API** é um serviço web para gerenciamento de um ecossistema de e-commerce/pedidos, contemplando o fluxo entre **Usuários (Clientes)**, **Pedidos**, **Itens de Pedido**, **Produtos**, **Categorias** e **Pagamentos**.

O projeto foi construído seguindo os padrões da indústria:
- **Padrão DTO (Data Transfer Objects):** Desacopla a camada de banco de dados da camada de apresentação, protegendo atributos sensíveis (como senha em `UserDTO` via `WRITE_ONLY`) e prevenindo serializações cíclicas.
- **Validação de Dados:** Utilização de Jakarta Bean Validation (`@Valid`, `@NotBlank`, `@Positive`, `@Email`, etc.) diretamente nos DTOs.
- **Tratamento Centralizado de Exceções:** `@ControllerAdvice` padronizando respostas de erro com timestamp, status HTTP, mensagem descritiva e URI solicitada.
- **Multi-ambiente com Spring Profiles:** Alternância ágil entre banco PostgreSQL persistente em container Docker (`dev`) e banco volátil em memória H2 (`test`).
- **Database Seeding Automático:** Carga de dados de teste ao inicializar o perfil `test` via `CommandLineRunner`.

---

## 🚧 Status e Observação

> ⚠️ **Atenção:** O projeto encontra-se atualmente em evolução ativa. A base essencial do domínio, as operações CRUD com DTOs e validações, e a infraestrutura de banco de dados com Docker Compose foram implementadas, com melhorias adicionais (como segurança, paginação e migrações) no planejamento da trilha de estudos.

---

## ✨ Funcionalidades e Recursos

- **CRUD de Usuários:** Cadastro, consulta por ID, listagem de todos, atualização de dados e remoção com integridade referencial.
- **CRUD de Produtos:** Gestão de catálogo de produtos com preço, descrição, imagem e vinculação com categorias.
- **CRUD de Categorias:** Organização hierárquica/temática de produtos (relação N:N).
- **CRUD de Pedidos:** Registro de ordens de compra associadas a clientes e produtos, com cálculo automático de subtotal dos itens e total do pedido.
- **Status do Pedido:** Controle do ciclo de vida do pedido (`Waiting_Payment`, `Paid`, `Shipped`, `Delivered`, `Canceled`).
- **Pagamentos:** Registro de pagamento com momento e vínculo exclusivo com o pedido correspondente (1:1).

---

## 🧩 Modelo de Domínio

```text
       ┌──────────┐ 1        * ┌──────────┐
       │   User   ├────────────┤  Order   │
       └──────────┘            └────┬─────┘
                                    │ 1
                                    │
                                    │ 1
                               ┌────┴─────┐
                               │ Payment  │
                               └──────────┘
                                    │ 1
                                    │
                                    │ *
       ┌──────────┐ *        * ┌────┴─────┐ *        * ┌──────────┐
       │ Category ├────────────┤ Product  ├────────────┤OrderItem │
       └──────────┘            └──────────┘            └──────────┘
```

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 21 (compatível com JDK 21+)
- **Framework:** Spring Boot 4.1.1
- **Persistência / ORM:** Spring Data JPA / Hibernate
- **Banco de Dados Relacional:** PostgreSQL 16+ (via Docker Compose)
- **Banco de Dados em Memória:** H2 Database (para testes rápidos)
- **Containerização:** Docker & Docker Compose
- **Validação:** Jakarta Bean Validation (Hibernate Validator)
- **Gerenciador de Dependências:** Apache Maven (com Maven Wrapper embutido)
- **Testes:** JUnit 5, Mockito e Spring Boot Test

---

## 📋 Requisitos

Para compilar e executar o projeto, você precisará de:

| Requisito | Versão Mínima | Descrição |
| :--- | :--- | :--- |
| **Java (JDK)** | **21** ou superior | Kit de Desenvolvimento Java configurado (`JAVA_HOME`) |
| **Docker & Docker Compose** | 20.x+ / 2.x+ | Necessário para subir o container do PostgreSQL no perfil `dev` |
| **Git** | 2.x+ | Para versionamento e clonar o repositório |
| **Maven** | *(Opcional)* | Script `./mvnw` embutido dispensando instalação manual do Maven |
| **Postman / Insomnia / cURL** | Qualquer | Para testar os endpoints da API REST |

---

## ⚙️ Ambientes e Perfis de Execução (Profiles)

A aplicação utiliza o recurso de perfis do Spring Boot para separar o ambiente de banco de dados:

| Perfil | Arquivo de Configuração | Banco de Dados | Finalidade |
| :--- | :--- | :--- | :--- |
| **`dev`** | `application-dev.properties` | **PostgreSQL (Docker)** | Ambiente de desenvolvimento real com persistência em disco |
| **`test`** | `application-test.properties` | **H2 Database (Memória)** | Testes rápidos sem necessidade do Docker, com carga inicial automática |

A alternância é feita no arquivo principal `src/main/resources/application.properties`:
```properties
# Alterne entre 'dev' (Postgres/Docker) e 'test' (H2 em memória)
spring.profiles.active=dev
```

---

## 🚀 Como Rodar o Projeto

### 1. Clonar o Repositório

```bash
git clone <URL_DO_REPOSITORIO>
cd course
```

### 2. Subir o Banco de Dados com Docker Compose

Para utilizar o perfil `dev` (PostgreSQL), inicie o container do banco:

```bash
docker compose up -d
```

Para verificar se o container está saudável e rodando na porta `5432`:
```bash
docker ps
```

### 3. Escolher o Perfil de Execução

No arquivo `src/main/resources/application.properties`, confirme se o perfil desejado está ativo:
- `spring.profiles.active=dev` (utiliza o PostgreSQL no Docker)
- `spring.profiles.active=test` (utiliza o H2 em memória)

### 4. Executar via Maven Wrapper

- **No Linux ou macOS:**
  ```bash
  chmod +x mvnw
  ./mvnw spring-boot:run
  ```

- **No Windows (Prompt / PowerShell):**
  ```cmd
  mvnw.cmd spring-boot:run
  ```

A aplicação inicializará por padrão na porta **8080** (`http://localhost:8080`).

---

### 5. Executar o Pacote JAR

Caso queira gerar e executar o arquivo empacotado `.jar`:

```bash
./mvnw clean package
java -jar target/course-0.0.1-SNAPSHOT.jar
```

---

### 6. Executar os Testes Automatizados

```bash
./mvnw test
```

---

## 🗄️ Consultando os Bancos de Dados

### PostgreSQL no Docker (Perfil `dev`)

Você pode inspecionar e fazer consultas no PostgreSQL rodando direto no terminal via container Docker:

```bash
# Acessar o console interativo do PostgreSQL (psql)
docker exec -it postgres_db psql -U devryan -d course_project
```

Comandos úteis no `psql`:
- `\dt` — Lista todas as tabelas criadas automaticamente pelo Hibernate.
- `SELECT * FROM tb_user;` — Consulta os usuários cadastrados.
- `\q` — Sai do console do banco.

Também é possível conectar ferramentas visuais (DBeaver, pgAdmin, IntelliJ Database Tools) usando:
- **Host:** `localhost` | **Porta:** `5432`
- **Banco de Dados:** `course_project`
- **Usuário:** `devryan` | **Senha:** `devryan`

---

### Console Web H2 (Perfil `test`)

Quando o perfil ativo for `test`, o painel web do H2 estará disponível no navegador:

- **URL:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **Driver Class:** `org.h2.Driver`
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User Name:** `sa`
- **Password:** *(deixe em branco)*

---

## 📡 Endpoints da API

Abaixo estão listadas as principais rotas disponíveis:

### 👤 Usuários (`/users`)

| Método | Endpoint | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- |
| `GET` | `/users` | Lista todos os usuários cadastrados | `200 OK` |
| `GET` | `/users/{id}` | Busca um usuário por ID | `200 OK` |
| `POST` | `/users` | Cadastra um novo usuário | `201 Created` |
| `PUT` | `/users/{id}` | Atualiza dados de um usuário existente | `200 OK` |
| `DELETE` | `/users/{id}` | Remove um usuário por ID | `204 No Content` |

**Exemplo de Payload (POST / PUT `/users`):**
```json
{
  "name": "Ryan Dev",
  "email": "ryan@gmail.com",
  "phone": "988888888",
  "password": "secretPassword123"
}
```

---

### 📦 Produtos (`/products`)

| Método | Endpoint | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- |
| `GET` | `/products` | Lista todos os produtos | `200 OK` |
| `GET` | `/products/{id}` | Busca um produto por ID | `200 OK` |
| `POST` | `/products` | Cadastra um novo produto | `201 Created` |
| `PUT` | `/products/{id}` | Atualiza dados de um produto | `200 OK` |
| `DELETE` | `/products/{id}` | Remove um produto por ID | `204 No Content` |

**Exemplo de Payload (POST / PUT `/products`):**
```json
{
  "name": "Monitor UltraWide 29\"",
  "description": "Monitor IPS 75Hz Full HD",
  "price": 1250.00,
  "imgUrl": "https://example.com/monitor.jpg"
}
```

---

### 🏷️ Categorias (`/categories`)

| Método | Endpoint | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- |
| `GET` | `/categories` | Lista todas as categorias e seus produtos | `200 OK` |
| `GET` | `/categories/{id}` | Busca uma categoria por ID | `200 OK` |
| `POST` | `/categories` | Cadastra uma nova categoria | `201 Created` |
| `PUT` | `/categories/{id}` | Atualiza o nome da categoria | `200 OK` |
| `DELETE` | `/categories/{id}` | Remove uma categoria | `204 No Content` |

**Exemplo de Payload (POST / PUT `/categories`):**
```json
{
  "name": "Informática"
}
```

---

### 📑 Pedidos (`/orders`)

| Método | Endpoint | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- |
| `GET` | `/orders` | Lista todos os pedidos com cliente, itens e total | `200 OK` |
| `GET` | `/orders/{id}` | Busca os detalhes de um pedido por ID | `200 OK` |
| `POST` | `/orders` | Cria um novo pedido | `201 Created` |
| `PUT` | `/orders/{id}` | Atualiza um pedido | `200 OK` |
| `DELETE` | `/orders/{id}` | Remove um pedido | `204 No Content` |

> **Nota sobre o Status do Pedido (`orderStatus`):**
> - `1`: `Waiting_Payment` (Aguardando Pagamento)
> - `2`: `Paid` (Pago)
> - `3`: `Shipped` (Enviado)
> - `4`: `Delivered` (Entregue)
> - `5`: `Canceled` (Cancelado)

---

## 🏛️ Estrutura de Camadas

O projeto segue a arquitetura clássica em camadas do ecossistema Spring:

```text
src/main/java/com/devryan/course/
├── config/              # Configurações de perfis e seed de banco (TestConfig)
├── dto/                 # Objetos de transferência de dados e validações Bean Validation
├── entities/            # Entidades de domínio JPA / Hibernate
│   ├── enums/           # Tipos enumerados (ex: OrderStatus)
│   └── pk/              # Chaves compostas de entidades (OrderItemPk)
├── repositories/        # Interfaces Spring Data JPA para acesso ao banco
├── resources/           # Controladores REST da API (@RestController)
│   └── exceptions/      # Interceptador global (@ControllerAdvice) e respostas de erro
└── services/            # Camada de lógica de negócio e regras da aplicação
    └── exceptions/      # Exceções personalizadas de negócio (ResourceNotFoundException, DatabaseException)
```

---

## ⚠️ Tratamento de Exceções

A API possui um interceptador global de exceções (`ResourceExceptionHandler`), garantindo que erros de validação ou de negócio retornem uma resposta padronizada:

```json
{
  "timestamp": "2026-10-14T17:15:00Z",
  "status": 404,
  "error": "Resource not found.",
  "message": "Resource not found. Id 999",
  "path": "/users/999"
}
```

Para erros de validação de campo (`400 Bad Request`), a resposta detalha os campos que infringiram as regras:
```json
{
  "timestamp": "2026-10-14T17:15:00Z",
  "status": 400,
  "error": "Validation error",
  "message": "email: Email inválido, name: Nome deve ter no mínimo 3 caracteres",
  "path": "/users"
}
```

---

## 🗺️ Roadmap e Próximos Passos

O desenvolvimento do projeto segue um plano de evolução técnica contínua:

### 🟢 Concluído
- [x] **1. Padrão DTO (Data Transfer Objects):**
  - Implementado para todas as entidades (`UserDTO`, `ProductDTO`, `CategoryDTO`, `OrderDTO`, `OrderItemDTO`).
  - Proteção de campos sensíveis (`password` configurada como `WRITE_ONLY`) e eliminação de loops de serialização JSON.
- [x] **2. Validações de Entrada (Spring Boot Validation):**
  - Anotações Bean Validation (`@Valid`, `@NotBlank`, `@Email`, `@Size`, `@Positive`, `@NotNull`).
  - Tratamento de `MethodArgumentNotValidException` com mensagens claras no `ResourceExceptionHandler`.
- [x] **3. Testes Unitários de DTOs:**
  - Testes cobrindo cenários positivos e negativos de validação de campos nos DTOs com Jakarta Validator.
- [x] **4. Migração e Integração com Banco Persistente (PostgreSQL):**
  - Perfil de desenvolvimento `dev` configurado em `application-dev.properties`.
  - Hibernate configurado com `ddl-auto=update` para geração automática do schema de tabelas no Postgres.
- [x] **5. Containerização do Banco com Docker Compose:**
  - Criação do `docker-compose.yaml` com PostgreSQL, volumes nomeados para persistência em disco e healthcheck.

---

### 🟡 Próximas Etapas Planejadas

- [ ] **Paginação e Ordenação:**
  - Migrar os métodos `findAll()` para utilizar `Pageable` e `Page<T>` do Spring Data JPA.
  - Suportar parâmetros de busca paginada e ordenação na URL (ex: `?page=0&size=10&sort=name,asc`).
- [ ] **Versionamento e Migrações de Banco (Flyway):**
  - Adicionar o Flyway Migration ao projeto.
  - Substituir o `ddl-auto` automático por scripts SQL versionados (`V1__create_tables.sql`, etc.) para governança rigorosa de schema.
- [ ] **Documentação Interativa com Swagger / OpenAPI:**
  - Integrar a biblioteca `springdoc-openapi-starter-webmvc-ui`.
  - Disponibilizar interface Swagger UI interativa (`/swagger-ui.html`) para documentação e teste das rotas.
- [ ] **Expansão da Cobertura de Testes Automatizados:**
  - Testes unitários com **Mockito** e **JUnit 5** para a camada de serviços (`UserService`, `ProductService`, etc.).
  - Testes de integração de endpoints REST com **MockMvc** (`@WebMvcTest` ou `@SpringBootTest`).
- [ ] **Segurança e Autenticação com Spring Security e JWT:**
  - Implementar autenticação stateless baseada em tokens JWT (JSON Web Tokens).
  - Criptografia de senhas com `BCryptPasswordEncoder`.
  - Controle de autorização por perfis de acesso (ex: `ROLE_ADMIN` e `ROLE_CLIENT`).
- [ ] **Containerização Completa da Aplicação:**
  - Criação de `Dockerfile` multi-stage otimizado para a aplicação Spring Boot.
  - Orquestração completa no Docker Compose (API + Banco de Dados).

---

## 👨‍💻 Autor

Desenvolvido por **Ryan** como parte de estudos aprofundados no ecossistema Spring Boot, Java e Docker.
