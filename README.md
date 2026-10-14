# 🛒 Course API - Web Service RESTful com Spring Boot

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-brightgreen?style=for-the-badge&logo=springboot)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apachemaven)
![H2 Database](https://img.shields.io/badge/Database-H2-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow?style=for-the-badge)

API RESTful desenvolvida com **Java** e **Spring Boot**, focada nas boas práticas de desenvolvimento backend, arquitetura em camadas, mapeamento objeto-relacional com JPA/Hibernate, validações de entrada e tratamento robusto de exceções.

> [!IMPORTANT]
> **Status do Projeto: Em Desenvolvimento 🚧**
> Este projeto **ainda não foi finalizado**! A base da aplicação, o modelo de domínio completo, a implementação do padrão DTO e as validações de dados já foram concluídos. Novas funcionalidades de produção (como autenticação, paginação, migrações e banco de dados real) estão em andamento. Veja a seção [Roadmap e Próximos Passos](#-roadmap-e-próximos-passos) para mais detalhes do planejamento.

---

## 📌 Sumário

- [O que é o projeto](#-o-que-é-o-projeto)
- [Status e Observação](#-status-e-observação)
- [Funcionalidades e Recursos](#-funcionalidades-e-recursos)
- [Modelo de Domínio](#-modelo-de-domínio)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Requisitos](#-requisitos)
- [Como Rodar o Projeto](#-como-rodar-o-projeto)
  - [1. Clonar o Repositório](#1-clonar-o-repositório)
  - [2. Executar via Maven Wrapper](#2-executar-via-maven-wrapper)
  - [3. Executar o Pacote JAR](#3-executar-o-pacote-jar)
  - [4. Executar os Testes Automatizados](#4-executar-os-testes-automatizados)
- [Acesso ao Banco de Dados (Console H2)](#-acesso-ao-banco-de-dados-console-h2)
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
- **Database Seeding Automático:** Carga de dados de teste ao inicializar o perfil `test` via `CommandLineRunner`.

---

## 🚧 Status e Observação

> ⚠️ **Atenção:** O projeto encontra-se atualmente em evolução ativa. A base essencial do domínio e as operações CRUD principais com DTOs e validações foram implementadas, mas diversas melhorias técnicas e arquiteturais ainda estão sendo adicionadas passo a passo para transformar este projeto em uma API completa de nível de produção.

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
- **Banco de Dados em Memória:** H2 Database
- **Banco de Dados em Produção:** Driver PostgreSQL pronto para configuração
- **Validação:** Jakarta Bean Validation (Hibernate Validator)
- **Gerenciador de Dependências:** Apache Maven (com Maven Wrapper embutido)
- **Testes:** JUnit 5, Mockito e Spring Boot Test

---

## 📋 Requisitos

Para clonar, compilar e executar o projeto, você precisará de:

| Requisito | Versão Mínima | Descrição |
| :--- | :--- | :--- |
| **Java (JDK)** | **21** ou superior | Kit de Desenvolvimento Java instalado e configurado nas variáveis de ambiente (`JAVA_HOME`) |
| **Git** | 2.x+ | Para clonar o repositório |
| **Maven** | *(Opcional)* | O projeto já conta com o script `./mvnw` embutido, dispensando a instalação prévia do Maven |
| **Postman / Insomnia / cURL** | Qualquer | *(Opcional)* Para testar e consumir as requisições HTTP |

Verifique a instalação do Java com:
```bash
java -version
```

---

## 🚀 Como Rodar o Projeto

### 1. Clonar o Repositório

```bash
git clone <URL_DO_REPOSITORIO>
cd course
```

### 2. Executar via Maven Wrapper

O projeto já inclui o executável do Maven Wrapper. Não é necessário instalar o Maven globalmente.

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

### 3. Executar o Pacote JAR

Caso queira gerar e executar o arquivo empacotado `.jar`:

1. Gere o pacote:
   ```bash
   ./mvnw clean package
   ```
2. Execute o arquivo gerado em `target/`:
   ```bash
   java -jar target/course-0.0.1-SNAPSHOT.jar
   ```

---

### 4. Executar os Testes Automatizados

O projeto conta com suíte de testes unitários e de validação de DTOs:

```bash
./mvnw test
```

---

## 🗄️ Acesso ao Banco de Dados (Console H2)

O perfil ativo padrão é o de teste (`test`), que utiliza o banco de dados em memória H2 com carga inicial automática de dados (`TestConfig.java`).

Para acessar o painel administrativo do H2 via navegador:

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
  "name": "Maria Brown",
  "email": "maria@gmail.com",
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

O desenvolvimento do projeto segue um plano de evolução técnica contínua. Abaixo está o status detalhado do que já foi concluído e das próximas etapas planejadas:

### 🟢 Já Concluído
- [x] **1. Padrão DTO (Data Transfer Objects):**
  - Implementado para todas as entidades (`UserDTO`, `ProductDTO`, `CategoryDTO`, `OrderDTO`, `OrderItemDTO`).
  - Ocultação de dados sensíveis (`password` configurada como `WRITE_ONLY`) e eliminação de loops de serialização JSON.
- [x] **2. Validações de Entrada (Spring Boot Validation):**
  - Anotações Bean Validation (`@Valid`, `@NotBlank`, `@Email`, `@Size`, `@Positive`, `@NotNull`).
  - Tratamento de `MethodArgumentNotValidException` com mensagens claras no `ResourceExceptionHandler`.
- [x] **3. Testes Unitários de DTOs:**
  - Validação dos cenários positivos e negativos de validação de campos nos DTOs com Jakarta Validator.

---

### 🟡 Planejado para as Próximas Etapas

- [ ] **3. Paginação e Ordenação:**
  - Migrar os métodos `findAll()` para utilizar `Pageable` e `Page<T>` do Spring Data JPA.
  - Suportar parâmetros de busca paginada e ordenação na URL (ex: `?page=0&size=10&sort=name,asc`).
- [ ] **4. Migração para Banco de Dados Relacional Persistente (PostgreSQL):**
  - Configurar perfil de desenvolvimento e produção (`application-dev.properties` / `application-prod.properties`).
  - Transicionar do banco em memória (H2) para um banco PostgreSQL permanente (driver já adicionado no `pom.xml`).
- [ ] **5. Versionamento e Migrações de Banco (Flyway):**
  - Adicionar o Flyway Migration ao projeto.
  - Substituir `ddl-auto` automático por scripts SQL versionados (`V1__create_tables.sql`, etc.) para controle rigoroso de schema.
- [ ] **6. Documentação Interativa com Swagger / OpenAPI:**
  - Integrar a biblioteca `springdoc-openapi-starter-webmvc-ui`.
  - Disponibilizar interface Swagger UI interativa (`/swagger-ui.html`) para documentação e teste das rotas.
- [ ] **7. Expansão da Cobertura de Testes Automatizados:**
  - Implementar testes unitários com **Mockito** e **JUnit 5** para a camada de serviços (`UserService`, `ProductService`, etc.).
  - Implementar testes de integração de endpoints REST com **MockMvc** (`@WebMvcTest` ou `@SpringBootTest`).
- [ ] **8. Segurança e Autenticação com Spring Security e JWT:**
  - Implementar autenticação stateless baseada em tokens JWT (JSON Web Tokens).
  - Criptografia de senhas com `BCryptPasswordEncoder`.
  - Controle de autorização por perfis de acesso (ex: `ROLE_ADMIN` e `ROLE_CLIENT`).
- [ ] **9. Containerização com Docker & Docker Compose:**
  - Criação de `Dockerfile` otimizado para a aplicação Spring Boot.
  - Criação de arquivo `docker-compose.yml` para orquestrar a aplicação em conjunto com o container do PostgreSQL.

---

## 👨‍💻 Autor

Desenvolvido por **Ryan** como parte de estudos aprofundados no ecossistema Spring Boot e Java.
