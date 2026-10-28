# 🛒 Course API - Web Service RESTful com Spring Boot

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-brightgreen?style=for-the-badge&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Docker-blue?style=for-the-badge&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker)
![H2 Database](https://img.shields.io/badge/Database-H2%20(Test)-blue?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apachemaven)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow?style=for-the-badge)

API RESTful desenvolvida com **Java 21** e **Spring Boot**, focada nas boas práticas de engenharia de software backend, arquitetura limpa em camadas, mapeamento objeto-relacional com JPA/Hibernate, validações declarativas com Jakarta Bean Validation, tratamento centralizado de exceções, paginação dinâmica com Spring Data JPA, modernização com Java Records e persistência em banco relacional **PostgreSQL** conteinerizado via **Docker Compose**.

> [!IMPORTANT]
> **Status do Projeto: Em Desenvolvimento Ativo 🚧**
> O projeto já conta com o modelo de domínio completo, DTOs com validações, tratamento robusto de erros, suporte a múltiplos ambientes (`dev` com PostgreSQL em Docker e `test` com H2 em memória), variáveis de ambiente desacopladas via arquivo `.env`, carga automática de dados de teste (50+ registros realistas), **paginação e ordenação dinâmica com `Pageable`** em todos os endpoints de listagem e início da adoção de **Java Records**.
>
> Veja a seção [Roadmap e Próximos Passos](#-roadmap-e-próximos-passos) para consultar as etapas concluídas e futuras.

---

## 📌 Sumário

- [O que é o projeto](#-o-que-é-o-projeto)
- [Status e Observação](#-status-e-observação)
- [Funcionalidades e Recursos](#-funcionalidades-e-recursos)
- [Modelo de Domínio](#-modelo-de-domínio)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Requisitos](#-requisitos)
- [Variáveis de Ambiente (.env)](#-variáveis-de-ambiente-env)
- [Ambientes e Perfis de Execução (Profiles)](#-ambientes-e-perfis-de-execução-profiles)
- [Como Rodar o Projeto](#-como-rodar-o-projeto)
  - [1. Clonar o Repositório](#1-clonar-o-repositório)
  - [2. Configurar o Arquivo `.env`](#2-configurar-o-arquivo-env)
  - [3. Subir o Banco de Dados com Docker Compose](#3-subir-o-banco-de-dados-com-docker-compose)
  - [4. Escolher o Perfil de Execução](#4-escolher-o-perfil-de-execução)
  - [5. Executar via Maven Wrapper](#5-executar-via-maven-wrapper)
  - [6. Executar o Pacote JAR](#6-executar-o-pacote-jar)
  - [7. Executar os Testes Automatizados](#7-executar-os-testes-automatizados)
- [Consultando os Bancos de Dados](#-consultando-os-bancos-de-dados)
  - [PostgreSQL no Docker (Perfil `dev`)](#postgresql-no-docker-perfil-dev)
  - [Console Web H2 (Perfil `test`)](#console-web-h2-perfil-test)
- [Endpoints da API](#-endpoints-da-api)
  - [Formato de Resposta Paginada](#formato-de-resposta-paginada-spring-data-page)
  - [👤 Usuários (`/users`)](#-usuários-users)
  - [📦 Produtos (`/products`)](#-produtos-products)
  - [🏷️ Categorias (`/categories`)](#️-categorias-categories)
  - [📑 Pedidos (`/orders`)](#-pedidos-orders)
- [Estrutura de Camadas](#-estrutura-de-camadas)
- [Tratamento de Exceções](#-tratamento-de-exceções)
- [Roadmap e Próximos Passos](#-roadmap-e-próximos-passos)
- [Autor](#-autor)

---

## 📖 O que é o projeto

O **Course API** é um serviço web para gerenciamento de um ecossistema de e-commerce e pedidos, contemplando o ciclo completo entre **Usuários (Clientes)**, **Pedidos**, **Itens de Pedido**, **Produtos**, **Categorias** e **Pagamentos**.

O projeto foi construído seguindo os padrões e convenções modernas da indústria:
- **Padrão DTO (Data Transfer Objects):** Desacopla a camada de persistência da camada de apresentação HTTP, ocultando campos sensíveis (como a senha em `UserDTO` via `WRITE_ONLY`) e prevenindo serializações cíclicas infinitas no Jackson.
- **Java Records & DTOs Especializados:** Modernização com `record` para classes imutáveis e concisas (ex: `CategoryDTO`) e separação de responsabilidades com DTO de detalhes (`CategoryDetailsDTO`), otimizando as consultas ao retornar associações de produtos apenas quando um item específico for consultado.
- **Paginação e Ordenação Dinâmica (Spring Data Pageable):** Todas as operações de listagem (`findAll`) utilizam paginação via banco de dados (`Pageable`, `Page<T>`, `@PageableDefault`), evitando sobrecarga de tráfego de rede e consumo excessivo de memória.
- **Validação Declarativa de Dados:** Anotações Jakarta Bean Validation (`@Valid`, `@NotBlank`, `@Positive`, `@Email`, `@Size`, `@NotNull`) nos DTOs, validando a integridade das requisições antes de qualquer operação de persistência.
- **Tratamento Global de Exceções:** `@ControllerAdvice` padronizando respostas de erro com timestamp, status HTTP, mensagem descritiva e URI solicitada.
- **Gestão Desacoplada de Credenciais (`.env`):** Configuração segura através de variáveis de ambiente no arquivo `.env`, consumidas dinamicamente pelo `docker-compose.yaml` e pelo Spring Boot via `spring.config.import=optional:file:.env[.properties]`.
- **Multi-ambiente com Spring Profiles:** Alternância transparente entre ambiente de desenvolvimento com PostgreSQL em container Docker (`dev`) e ambiente rápido com banco H2 em memória (`test`).
- **Database Seeding Automatizado:** Carga inicial automática de 50+ registros realistas (categorias, produtos de diversos segmentos, usuários brasileiros, pedidos com itens e pagamentos) ao inicializar o perfil `test`.

---

## 🚧 Status e Observação

> ⚠️ **Atenção:** O projeto encontra-se em constante evolução. Os módulos de domínio, persistência com PostgreSQL conteinerizado, variáveis via `.env`, validações, DTOs, records, paginação e carga de dados de teste já foram entregues. As próximas etapas contemplam migrações com Flyway, documentação interativa OpenAPI/Swagger e segurança stateless com JWT.

---

## ✨ Funcionalidades e Recursos

- **Gestão de Usuários (Clientes):**
  - Listagem paginada e ordenada com parâmetros configuráveis (`page`, `size`, `sort`).
  - Busca por ID com tratamento de entidade não encontrada (`404 Not Found`).
  - Cadastro e atualização com validações rigorosas de nome, e-mail, telefone e senha.
  - Exclusão com proteção de integridade referencial (`400 Bad Request` se possuir pedidos vinculados).
- **Gestão de Produtos:**
  - Listagem paginada por nome, preço e ID.
  - Busca detalhada por ID com categorias associadas.
  - Cadastro e atualização com validação de preço positivo e campos obrigatórios.
  - Exclusão segura no catálogo.
- **Gestão de Categorias:**
  - Listagem paginada rápida utilizando **Java Record** (`CategoryDTO`), trafegando apenas ID e nome.
  - Busca por ID detalhada via `CategoryDetailsDTO`, incluindo a coleção de produtos pertencentes à categoria.
  - Cadastro, atualização e remoção de categorias.
- **Gestão de Pedidos:**
  - Listagem paginada e ordenada por data/momento (`moment`).
  - Associação de múltiplos itens de pedido com cálculo automático de subtotal (`price * quantity`) e valor total do pedido.
  - Controle de ciclo de vida via enum `OrderStatus` (`Waiting_Payment`, `Paid`, `Shipped`, `Delivered`, `Canceled`).
  - Associação com cliente solicitante e suporte a registro de pagamento vinculado (1:1).

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

- **Linguagem:** Java 21 (LTS)
- **Framework Principal:** Spring Boot 4.1.1
- **Módulos Spring:**
  - Spring Data JPA (persistência, repositórios e `Pageable`)
  - Spring Web / MVC (controladores RESTful)
  - Spring Boot Validation (validação com Hibernate Validator)
- **Bancos de Dados:**
  - PostgreSQL 16+ (banco relacional principal via Docker Compose)
  - H2 Database (banco relacional em memória para testes)
- **Containerização:** Docker & Docker Compose
- **Configuração de Ambiente:** Dotenv (`.env` lido pelo Docker e Spring Boot)
- **Gerenciador de Dependências:** Apache Maven 3.9+ (com Maven Wrapper `./mvnw` embutido)
- **Testes Automatizados:** JUnit 5, Mockito e Jakarta Bean Validation

---

## 📋 Requisitos

Para compilar e executar o projeto localmente, certifique-se de possuir:

| Requisito | Versão Mínima | Descrição |
| :--- | :--- | :--- |
| **Java (JDK)** | **21** ou superior | Kit de Desenvolvimento Java configurado (`JAVA_HOME`) |
| **Docker & Docker Compose** | 20.x+ / 2.x+ | Necessário para subir o container do PostgreSQL no perfil `dev` |
| **Git** | 2.x+ | Para clonar o repositório e controle de versão |
| **Maven** | *(Opcional)* | Script `./mvnw` embutido dispensando instalação manual do Maven |
| **Cliente HTTP** | Qualquer | Postman, Insomnia, VS Code REST Client ou cURL |

---

## 🔐 Variáveis de Ambiente (.env)

O projeto adota o padrão de variáveis de ambiente para desacoplar credenciais do código-fonte. O arquivo `.env` na raiz do projeto é utilizado simultaneamente pelo **Docker Compose** e pelo Spring Boot (no perfil `dev`):

Crie ou edite o arquivo `.env` baseado no modelo disponível (`.env.example`):

```bash
# Copie o arquivo de exemplo
cp .env.example .env
```

Conteúdo esperado no arquivo `.env`:

```properties
KEY_POSTGRES_DB=course_project
KEY_POSTGRES_USER=devryan
KEY_POSTGRES_PASSWORD=devryan
KEY_POSTGRES_PORT=5432
```

> [!NOTE]
> O arquivo `.env` está incluído no `.gitignore` por razões de segurança. Caso queira alterar a porta ou as senhas locais, modifique os valores no seu `.env` antes de inicializar o container.

---

## ⚙️ Ambientes e Perfis de Execução (Profiles)

A aplicação utiliza os perfis do Spring Boot para gerenciar o ambiente de banco de dados:

| Perfil | Arquivo de Configuração | Banco de Dados | Finalidade |
| :--- | :--- | :--- | :--- |
| **`dev`** | `application-dev.properties` | **PostgreSQL (Docker)** | Ambiente de desenvolvimento real com persistência em disco via `.env` |
| **`test`** | `application-test.properties` | **H2 Database (Memória)** | Testes locais e execução sem Docker, com carga automática de 50+ registros |

A alternância é realizada no arquivo `src/main/resources/application.properties`:
```properties
spring.application.name=course
# Alterne entre 'dev' (Postgres/Docker) e 'test' (H2 em memória)
spring.profiles.active=dev
spring.jpa.open-in-view=true
```

---

## 🚀 Como Rodar o Projeto

### 1. Clonar o Repositório

```bash
git clone <URL_DO_REPOSITORIO>
cd course
```

### 2. Configurar o Arquivo `.env`

Crie uma cópia do arquivo de exemplo para gerar o seu `.env` local:

```bash
cp .env.example .env
```

### 3. Subir o Banco de Dados com Docker Compose

Para rodar com o perfil `dev` (PostgreSQL), inicie o serviço do banco de dados em segundo plano:

```bash
docker compose up -d
```

Verifique se o container está ativo e saudável:
```bash
docker compose ps
```

### 4. Escolher o Perfil de Execução

No arquivo `src/main/resources/application.properties`, confirme o perfil ativo:
- `spring.profiles.active=dev` — Conecta ao PostgreSQL via Docker.
- `spring.profiles.active=test` — Executa com banco H2 em memória (não necessita do Docker).

### 5. Executar via Maven Wrapper

- **No Linux ou macOS:**
  ```bash
  chmod +x mvnw
  ./mvnw spring-boot:run
  ```

- **No Windows (CMD ou PowerShell):**
  ```cmd
  mvnw.cmd spring-boot:run
  ```

A aplicação estará disponível em **`http://localhost:8080`**.

---

### 6. Executar o Pacote JAR

Caso queira gerar e rodar o arquivo empacotado `.jar`:

```bash
./mvnw clean package
java -jar target/course-0.0.1-SNAPSHOT.jar
```

---

### 7. Executar os Testes Automatizados

Para executar a suíte de testes unitários e de integração utilizando o perfil volátil em memória (`test`):

```bash
./mvnw test -Dspring.profiles.active=test
```

> [!TIP]
> A flag `-Dspring.profiles.active=test` garante que os testes executem no banco em memória H2, dispensando a necessidade do container PostgreSQL estar rodando.

---

## 🗄️ Consultando os Bancos de Dados

### PostgreSQL no Docker (Perfil `dev`)

Para abrir uma sessão interativa no PostgreSQL via terminal:

```bash
# Acessar o console do psql dentro do container
docker exec -it postgres_db psql -U devryan -d course_project
```

Comandos úteis no `psql`:
- `\dt` — Lista todas as tabelas criadas pelo Hibernate.
- `SELECT * FROM tb_user;` — Consulta usuários cadastrados.
- `SELECT * FROM tb_product;` — Consulta produtos cadastrados.
- `\q` — Sai do console interativo.

Para conectar via interface gráfica (DBeaver, pgAdmin, IntelliJ Database Navigator):
- **Host:** `localhost` | **Porta:** `${KEY_POSTGRES_PORT}` (padrão `5432`)
- **Database:** `${KEY_POSTGRES_DB}` (padrão `course_project`)
- **User:** `${KEY_POSTGRES_USER}` (padrão `devryan`)
- **Password:** `${KEY_POSTGRES_PASSWORD}` (padrão `devryan`)

---

### Console Web H2 (Perfil `test`)

Quando o perfil ativo for `test`, o painel web do banco H2 fica acessível pelo navegador:

- **URL:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **Driver Class:** `org.h2.Driver`
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User Name:** `sa`
- **Password:** *(deixe em branco)*

---

## 📡 Endpoints da API

### Formato de Resposta Paginada (Spring Data Page)

Todas as requisições `GET` de listagem retornam o formato padrão de paginação do Spring Data:

```json
{
  "content": [ ... ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": { "sorted": true, "unsorted": false, "empty": false },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 5,
  "totalElements": 50,
  "last": false,
  "size": 10,
  "number": 0,
  "sort": { "sorted": true, "unsorted": false, "empty": false },
  "numberOfElements": 10,
  "first": true,
  "empty": false
}
```

Parâmetros de paginação e ordenação aceitos na URL:
- `page`: Número da página (base 0, padrão `0`).
- `size`: Quantidade de elementos por página (padrão `10`).
- `sort`: Campo e direção da ordenação (ex: `sort=name,asc` ou `sort=price,desc`).

---

### 👤 Usuários (`/users`)

| Método | Endpoint | Parâmetros / Padrão | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- | :--- |
| `GET` | `/users` | `page=0, size=10, sort=name,asc` | Lista usuários paginados e ordenados por nome | `200 OK` |
| `GET` | `/users/{id}` | - | Busca detalhes de um usuário por ID | `200 OK` |
| `POST` | `/users` | Body JSON | Cadastra um novo usuário | `201 Created` |
| `PUT` | `/users/{id}` | Body JSON | Atualiza os dados de um usuário | `200 OK` |
| `DELETE` | `/users/{id}` | - | Remove um usuário por ID | `204 No Content` |

**Exemplo de Payload (POST / PUT `/users`):**
```json
{
  "name": "Ryan Dev",
  "email": "ryan@gmail.com",
  "phone": "988888888",
  "password": "secretPassword123"
}
```

> **Segurança:** O atributo `password` possui anotação `@JsonProperty(access = Access.WRITE_ONLY)`, nunca sendo exposto nas respostas das requisições `GET`, `POST` ou `PUT`.

---

### 📦 Produtos (`/products`)

| Método | Endpoint | Parâmetros / Padrão | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- | :--- |
| `GET` | `/products` | `page=0, size=10, sort=name,asc` | Lista produtos paginados e ordenados por nome | `200 OK` |
| `GET` | `/products/{id}` | - | Busca um produto detalhado por ID | `200 OK` |
| `POST` | `/products` | Body JSON | Cadastra um novo produto | `201 Created` |
| `PUT` | `/products/{id}` | Body JSON | Atualiza os dados de um produto | `200 OK` |
| `DELETE` | `/products/{id}` | - | Remove um produto por ID | `204 No Content` |

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

| Método | Endpoint | Parâmetros / Padrão | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- | :--- |
| `GET` | `/categories` | `page=0, size=10, sort=id,asc` | Lista categorias paginadas em formato leve (`CategoryDTO` record: `id`, `name`) | `200 OK` |
| `GET` | `/categories/{id}` | - | Retorna categoria com coleção detalhada de produtos vinculados (`CategoryDetailsDTO`) | `200 OK` |
| `POST` | `/categories` | Body JSON | Cadastra uma nova categoria | `201 Created` |
| `PUT` | `/categories/{id}` | Body JSON | Atualiza o nome da categoria | `200 OK` |
| `DELETE` | `/categories/{id}` | - | Remove uma categoria por ID | `204 No Content` |

**Exemplo de Resposta (GET `/categories/{id}` - `CategoryDetailsDTO`):**
```json
{
  "id": 1,
  "name": "Eletrônicos",
  "items": [
    {
      "id": 11,
      "name": "Smart TV Samsung 55 OLED 4K",
      "description": "Painel OLED com pretos perfeitos",
      "price": 5499.0,
      "imgUrl": ""
    }
  ]
}
```

**Exemplo de Payload (POST / PUT `/categories`):**
```json
{
  "name": "Informática"
}
```

---

### 📑 Pedidos (`/orders`)

| Método | Endpoint | Parâmetros / Padrão | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- | :--- |
| `GET` | `/orders` | `page=0, size=10, sort=moment,asc` | Lista pedidos paginados e ordenados por data | `200 OK` |
| `GET` | `/orders/{id}` | - | Busca detalhes de um pedido por ID com itens e total | `200 OK` |
| `POST` | `/orders` | Body JSON | Cria um novo pedido | `201 Created` |
| `PUT` | `/orders/{id}` | Body JSON | Atualiza um pedido | `200 OK` |
| `DELETE` | `/orders/{id}` | - | Remove um pedido por ID | `204 No Content` |

> **Valores do Status do Pedido (`orderStatus`):**
> - `1`: `Waiting_Payment` (Aguardando Pagamento)
> - `2`: `Paid` (Pago)
> - `3`: `Shipped` (Enviado)
> - `4`: `Delivered` (Entregue)
> - `5`: `Canceled` (Cancelado)

---

## 🏛️ Estrutura de Camadas

O projeto segue a arquitetura multicamadas padrão do ecossistema Spring Boot:

```text
src/main/java/com/devryan/course/
├── config/              # Configurações de perfis e seed de banco (TestConfig)
├── dto/                 # DTOs, Java Records e regras de validação Bean Validation
│   ├── CategoryDTO.java          # Java Record com validação
│   ├── CategoryDetailsDTO.java   # DTO com coleção de produtos associados
│   ├── OrderDTO.java             # DTO de pedido com itens e total
│   ├── OrderItemDTO.java         # DTO de item de pedido e subtotal
│   ├── ProductDTO.java           # DTO de produto
│   └── UserDTO.java              # DTO de usuário com proteção WRITE_ONLY de senha
├── entities/            # Entidades JPA mapeadas para tabelas relacionais
│   ├── enums/           # Enumerados de domínio (ex: OrderStatus)
│   └── pk/              # Chaves primárias compostas (OrderItemPk)
├── repositories/        # Repositórios Spring Data JPA (UserRepository, etc.)
├── resources/           # Controladores REST da API (@RestController com @PageableDefault)
│   └── exceptions/      # Interceptador global (@ControllerAdvice) e StandardError
└── services/            # Camada de lógica de negócio e transações de serviço
    └── exceptions/      # Exceções de negócio (ResourceNotFoundException, DatabaseException)
```

---

## ⚠️ Tratamento de Exceções

A API possui um interceptador global de exceções (`ResourceExceptionHandler`), garantindo respostas padronizadas em JSON para todos os tipos de erros:

### Recurso Não Encontrado (`404 Not Found`)
```json
{
  "timestamp": "2026-10-28T14:00:00Z",
  "status": 404,
  "error": "Resource not found.",
  "message": "Resource not found. Id 999",
  "path": "/users/999"
}
```

### Falha de Validação de Campos (`400 Bad Request`)
Quando as regras do Jakarta Bean Validation falharem em requisições `POST` ou `PUT`:
```json
{
  "timestamp": "2026-10-28T14:00:00Z",
  "status": 400,
  "error": "Validation error",
  "message": "email: Email inválido, name: Nome deve ter no mínimo 3 caracteres",
  "path": "/users"
}
```

### Violação de Integridade Referencial (`400 Bad Request`)
Ao tentar excluir entidades com registros dependentes no banco (ex: usuário com pedidos vinculados):
```json
{
  "timestamp": "2026-10-28T14:00:00Z",
  "status": 400,
  "error": "Database error",
  "message": "Integrity violation: cannot delete resource with existing associations",
  "path": "/users/1"
}
```

---

## 🗺️ Roadmap e Próximos Passos

O desenvolvimento do projeto segue um roteiro técnico planejado e incremental:

### 🟢 Concluído

- [x] **1. Padrão DTO (Data Transfer Objects):**
  - Implementado para todas as entidades (`UserDTO`, `ProductDTO`, `CategoryDTO`, `OrderDTO`, `OrderItemDTO`).
  - Proteção de campos sensíveis (`password` configurada como `WRITE_ONLY`) e eliminação de recursão/loops cíclicos no JSON.
- [x] **2. Validações de Entrada (Spring Boot Validation):**
  - Anotações Bean Validation (`@Valid`, `@NotBlank`, `@Email`, `@Size`, `@Positive`, `@NotNull`).
  - Tratamento centralizado de `MethodArgumentNotValidException` detalhando campos inválidos.
- [x] **3. Testes Unitários de DTOs e Validações:**
  - Suíte de testes cobrindo validações de entrada positivas e negativas com Jakarta Validator.
- [x] **4. Migração e Integração com Banco Persistente (PostgreSQL):**
  - Perfil de desenvolvimento `dev` configurado com PostgreSQL.
  - Hibernate configurado com `ddl-auto=update` para geração automática das tabelas e relacionamentos.
- [x] **5. Containerização do Banco com Docker Compose:**
  - Criação do `docker-compose.yaml` com imagem PostgreSQL, persistência de volume nomeado e healthcheck.
- [x] **6. Desacoplamento de Credenciais via Arquivo `.env`:**
  - Extração de credenciais de banco para `.env` consumido tanto pelo Docker Compose quanto pelo Spring Boot (`spring.config.import=optional:file:.env[.properties]`).
  - Criação do `.env.example` para onboarding seguro no repositório.
- [x] **7. Paginação e Ordenação Dinâmica (Spring Data Pageable):**
  - Implementação de `Pageable` e `Page<T>` em todos os métodos `findAll()` da camada de serviços e controladores REST.
  - Configuração de valores padrão com `@PageableDefault` (páginas de 10 itens com ordenação definida por recurso).
  - Suporte completo a parâmetros na URL (`?page=0&size=10&sort=campo,asc|desc`).
- [x] **8. Modernização com Java Records & DTOs Especializados:**
  - Refatoração de `CategoryDTO` utilizando Java Record para maior imutabilidade e concisão.
  - Criação de `CategoryDetailsDTO` para evitar tráfego de dados desnecessário em listagens paginadas e carregar itens apenas na consulta por ID.
- [x] **9. Dataset de Teste e Homologação Robusto:**
  - Seed ampliado em `TestConfig` com 50+ registros realistas (categorias, produtos variados, usuários e pedidos com pagamentos) para validação prática de paginação.

---

### 🟡 Próximas Etapas Planejadas

- [ ] **1. Versionamento e Migrações de Banco de Dados (Flyway):**
  - Integrar a dependência do Flyway Migration.
  - Substituir o `ddl-auto=update` por scripts versionados em SQL (`V1__create_initial_schema.sql`, etc.) para controle rigoroso de evolução do banco.
- [ ] **2. Documentação Interativa com Swagger / OpenAPI:**
  - Adicionar a dependência `springdoc-openapi-starter-webmvc-ui`.
  - Configurar metadados da API e disponibilizar o painel interativo do Swagger UI (`/swagger-ui.html`).
- [ ] **3. Expansão da Cobertura de Testes Automatizados:**
  - Testes unitários com **Mockito** e **JUnit 5** cobrindo cenários de sucesso e exceções na camada de serviços (`UserService`, `ProductService`, etc.).
  - Testes de integração de controladores com **MockMvc** (`@WebMvcTest` e `@SpringBootTest`).
- [ ] **4. Migração Gradual dos demais DTOs para Java Records:**
  - Avaliar e migrar `ProductDTO`, `OrderItemDTO`, etc. para Java Records onde a imutabilidade for desejada.
- [ ] **5. Segurança e Autenticação com Spring Security e JWT:**
  - Implementar autenticação stateless com tokens JWT (JSON Web Tokens).
  - Criptografia de senhas com `BCryptPasswordEncoder`.
  - Controle de autorização baseado em perfis (ex: `ROLE_ADMIN` e `ROLE_CLIENT`).
- [ ] **6. Containerização Completa da Aplicação:**
  - Criação de `Dockerfile` multi-stage otimizado para o executável Spring Boot.
  - Orquestração única no `docker-compose.yaml` subindo tanto a API quanto o PostgreSQL juntos.

---

## 👨‍💻 Autor

Desenvolvido por **Ryan** como parte de estudos aprofundados no ecossistema Spring Boot, Java e Docker.
