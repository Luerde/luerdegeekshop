# Arquitetura do Sistema - Luerde Geek Shop

Este documento descreve a arquitetura da aplicação Luerde Geek Shop, uma aplicação web monolítica construída com Java e Spring Boot.

## 1. Visão Geral da Arquitetura

A aplicação segue uma arquitetura em camadas, típica de aplicações Spring Boot, mas com algumas particularidades. É uma aplicação monolítica onde o backend e o frontend são servidos pela mesma aplicação.

As camadas principais são:
-   **Camada de Apresentação (Presentation Layer):** Responsável por lidar com as requisições HTTP e renderizar as páginas para o usuário.
-   **Camada de Serviço (Service Layer):** Contém a lógica de negócio da aplicação. No estado atual, esta camada é bastante "magra" (thin).
-   **Camada de Acesso a Dados (Data Access Layer):** Responsável pela comunicação com o banco de dados.

## 2. Tecnologias Principais

-   **Backend:**
    -   **Java 21:** Linguagem de programação principal.
    -   **Spring Boot:** Framework principal que simplifica a criação de aplicações Spring.
    -   **Spring MVC:** Para a criação de controllers web (endpoints HTTP).
    -   **Spring Data JPA:** Para o acesso a dados e mapeamento objeto-relacional (ORM) com o Hibernate.
    -   **Spring Security:** Para a gestão de autenticação e autorização.
-   **Banco de Dados:**
    -   **MySQL:** Sistema de gerenciamento de banco de dados relacional.
    -   **Hibernate:** Implementação do JPA, responsável por traduzir as operações em comandos SQL e pelo controle do schema do banco de dados (configurado com `ddl-auto=update`).
-   **Frontend:**
    -   **Thymeleaf:** Motor de templates Java para renderização de HTML no lado do servidor.
    -   **HTML, CSS, JavaScript:** Tecnologias padrão para a construção das páginas web.
-   **Email:**
    -   **Spring Mail:** Configurado para o envio de emails.

## 3. Camadas da Aplicação

### 3.1. Camada de Apresentação (Controllers)

-   **Localização:** `src/main/java/com/luerde/luerdegeekshop/controller/`
-   **Descrição:** Esta camada é o ponto de entrada da aplicação. Ela é composta por `Controllers` que mapeiam as URLs para métodos específicos.
-   **Componentes:**
    -   `SiteController` e `AuthController` (`@Controller`): Responsáveis por retornar páginas HTML renderizadas pelo Thymeleaf. Eles contêm lógica de negócio significativa, como o processo de registro de usuário e finalização de compra, o que é um ponto de atenção na arquitetura.
    -   `ProductApiController` (`@RestController`): Expõe um endpoint JSON (`/api/products`) para fornecer dados de produtos, provavelmente consumido por JavaScript no frontend para carregar produtos dinamicamente.

### 3.2. Camada de Serviço (Service)

-   **Localização:** `src/main/java/com/luerde/luerdegeekshop/service/`
-   **Descrição:** Esta camada deveria conter a maior parte da lógica de negócio. No entanto, ela é atualmente muito "magra" (thin), com a lógica de negócio residindo principalmente nos controllers.
-   **Componentes:**
    -   `CustomUserDetailsService`: Componente essencial do Spring Security. É responsável por carregar os dados de um usuário do banco de dados durante o processo de autenticação.
    -   `EmailService`: Um serviço utilitário para o envio de emails.

### 3.3. Camada de Acesso a Dados (Repositories)

-   **Localização:** Interfaces no diretório principal do pacote (`com.luerde.luerdegeekshop`).
-   **Descrição:** Abstrai o acesso ao banco de dados. Utiliza o Spring Data JPA, que gera automaticamente as implementações para as interfaces.
-   **Componentes:**
    -   `UserRepository`, `ProductRepository`, `OrderRepository`: Interfaces que estendem `JpaRepository`. Elas fornecem métodos para operações CRUD (Create, Read, Update, Delete) e a capacidade de criar queries customizadas.

## 4. Modelo de Dados (Entidades)

-   **Localização:** `src/main/java/com/luerde/luerdegeekshop/`
-   **Descrição:** As entidades JPA representam as tabelas do banco de dados.
-   **Entidades Principais:**
    -   `User`: Representa um usuário da aplicação.
    -   `Product`: Representa um produto à venda na loja.
    -   `Order`: Representa um pedido.

## 5. Segurança

-   **Localização da Configuração:** `src/main/java/com/luerde/luerdegeekshop/SecurityConfig.java`
-   **Descrição:** A segurança é gerenciada pelo Spring Security.
-   **Características:**
    -   Utiliza `BCryptPasswordEncoder` para armazenar as senhas dos usuários de forma segura (hashed).
    -   Define quais URLs são públicas (ex: `/`, `/register`, `/api/products/**`) e quais exigem autenticação.
    -   Não há um controle de acesso baseado em papéis (Role-Based Access Control - RBAC) implementado; todos os usuários autenticados têm o mesmo nível de permissão.

## 6. Frontend

-   **Localização:** `src/main/resources/templates/` (arquivos HTML) e `src/main/resources/static/` (CSS, JS, imagens).
-   **Descrição:** O frontend é renderizado no lado do servidor usando o Thymeleaf. As páginas são servidas como HTML e podem usar JavaScript para interações dinâmicas, como buscar a lista de produtos da API.

## 7. Insight Arquitetural Crítico

Uma descoberta arquitetural importante, também destacada no dicionário de dados, é a falta de relacionamentos na tabela `orders`. No estado atual, a aplicação **não armazena a associação entre um pedido (`Order`), o usuário que o fez (`User`) e os produtos (`Product`) contidos nele**. Isso representa uma lacuna funcional crítica na funcionalidade de checkout. Para que o sistema de pedidos funcione corretamente, seria necessário implementar esses relacionamentos no modelo de dados (ex: `@ManyToOne` entre `Order` e `User`, e `@ManyToMany` entre `Order` e `Product`).
