package com.luerde.luerdegeekshop;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

import jakarta.persistence.CascadeType;

/**
 * Representa um usuário na loja.
 * A anotação @Entity marca esta classe como uma entidade JPA, o que significa que ela será mapeada para uma tabela no banco de dados.
 * A anotação @Table especifica o nome da tabela no banco de dados que esta entidade será mapeada.
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * O campo 'id' é a chave primária da tabela 'users'.
     * A anotação @Id marca este campo como a chave primária.
     * A anotação @GeneratedValue especifica que o valor do ID será gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;

    /**
     * O e-mail do usuário.
     * A anotação @Column(nullable = false, unique = true) indica que este campo não pode ser nulo e deve ser único no banco de dados.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * A senha do usuário (já criptografada).
     * A anotação @Column(nullable = false) indica que este campo não pode ser nulo no banco de dados.
     */
    @Column(nullable = false)
    private String password;

    /**
     * O nome de usuário do usuário.
     * A anotação @Column(nullable = false) indica que este campo não pode ser nulo no banco de dados.
     */
    @Column(nullable = false)
    private String username;

    @Column(name = "security_question")
    private String securityQuestion;

    @Column(name = "security_answer")
    private String securityAnswer;

    // Getters e Setters

    /**
     * Obtém o ID do usuário.
     * @return O ID do usuário.
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID do usuário.
     * @param id O ID do usuário.
     */
    public void setId(Long id) {
        this.id = id;
    }

      /**
     * Obtém a lista de pedidos do usuário.
     * @return A lista de pedidos do usuário.
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Define a lista de pedidos do usuário.
     * @param orders A lista de pedidos do usuário.
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    /**
     * Obtém o e-mail do usuário.
     * @return O e-mail do usuário.
     */
    public String getEmail() {
        return email;
    }


    /**
     * Define o e-mail do usuário.
     * @param email O e-mail do usuário.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtém a senha do usuário.
     * @return A senha do usuário.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Define a senha do usuário.
     * @param password A senha (espera-se que já esteja criptografada).
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtém o nome de usuário do usuário.
     * @return O nome de usuário do usuário.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Define o nome de usuário do usuário.
     * @param username O nome de usuário do usuário.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtém a pergunta de segurança do usuário.
     * @return A pergunta de segurança.
     */
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    /**
     * Define a pergunta de segurança do usuário.
     * @param securityQuestion A pergunta de segurança.
     */
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    /**
     * Obtém a resposta de segurança do usuário.
     * @return A resposta de segurança.
     */
    public String getSecurityAnswer() {
        return securityAnswer;
    }

    /**
     * Define a resposta de segurança do usuário.
     * @param securityAnswer A resposta de segurança.
     */
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    /**
     * Construtor padrão (sem argumentos).
     * O JPA (Java Persistence API) requer um construtor padrão para criar instâncias da entidade.
     */
    public User() {
    }
}