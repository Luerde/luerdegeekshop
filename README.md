# Manual de Instalação – Luerde GeekShop

Este manual fornece as instruções passo a passo para configurar e executar o projeto Luerde GeekShop em um ambiente de desenvolvimento local.

---

## 1. Pré-requisitos

Antes de começar, garanta que você tenha os seguintes softwares instalados e configurados em seu sistema:

*   **Java Development Kit (JDK):** Versão 21 ou superior.
*   **Apache Maven:** Ferramenta de automação de compilação. Você pode baixar no [site oficial do Maven](https://maven.apache.org/download.cgi).
*   **MySQL Server:** Versão 8 ou superior.
*   **Git:** Sistema de controle de versão para baixar o código-fonte.

**Observação sobre Apache:** O projeto utiliza um servidor web Tomcat embutido, fornecido pelo Spring Boot. Portanto, **não é necessário** ter um servidor Apache ou similar instalado para executar a aplicação localmente.

---

## 2. Download do Código-Fonte

Para obter o código-fonte do projeto, utilize o Git para clonar o repositório do GitHub.

1.  Abra um terminal ou prompt de comando.
2.  Navegue até o diretório onde você deseja salvar o projeto.
3.  Execute o seguinte comando (substitua a URL pelo link correto do seu repositório):

    ```bash
    git clone https://github.com/seu-usuario/luerdegeekshop.git
    ```

4.  Após a conclusão, entre no diretório do projeto:
    ```bash
    cd luerdegeekshop
    ```

---

## 3. Configuração do Banco de Dados

A aplicação requer um banco de dados MySQL para funcionar.

### a) Criação do Banco de Dados

1.  Abra seu cliente MySQL (seja por linha de comando, MySQL Workbench ou outro).
2.  Crie um novo banco de dados (schema) com o nome `luerdegeekshop`.

    ```sql
    CREATE DATABASE luerdegeekshop;
    ```

### b) Configuração da Conexão

1.  Com o projeto aberto em seu editor de código, navegue até o arquivo:
    `src/main/resources/application.properties`

2.  Localize as seguintes linhas e atualize-as com suas credenciais de acesso ao MySQL:

    ```properties
    # Database Configuration
    spring.datasource.url=jdbc:mysql://localhost:3306/luerdegeekshop
    spring.datasource.username=seu_usuario_root # Altere para seu usuário
    spring.datasource.password=sua_senha_aqui   # Altere para sua senha
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    ```

### c) Criação das Tabelas e Dados Iniciais

Não é necessário executar scripts de criação de tabelas manualmente. A aplicação está configurada com `spring.jpa.hibernate.ddl-auto=update`, o que significa que o Hibernate (a tecnologia de persistência de dados) irá criar ou atualizar as tabelas automaticamente quando a aplicação for iniciada.

Além disso, o arquivo `src/main/resources/data.sql` será executado em seguida, populando o banco de dados com dados iniciais de produtos.

---

## 4. Executando a Aplicação

Com o ambiente e o banco de dados configurados, você pode iniciar a aplicação.

1.  Abra um terminal na raiz do projeto.
2.  Execute o seguinte comando usando o Maven Wrapper (que já vem com o projeto):

    *   **No Linux ou macOS:**
        ```bash
        ./mvnw spring-boot:run
        ```
    *   **No Windows:**
        ```bash
        mvnw.cmd spring-boot:run
        ```

3.  Aguarde o Maven baixar as dependências e iniciar o servidor. Ao final, você verá uma mensagem indicando que a aplicação foi iniciada na porta `8080`.

4.  Abra seu navegador e acesse: [http://localhost:8080](http://localhost:8080)

Pronto! O Luerde GeekShop estará em execução no seu ambiente local.
