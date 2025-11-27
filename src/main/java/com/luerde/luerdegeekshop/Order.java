package com.luerde.luerdegeekshop;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Representa um pedido na loja.
 * A anotação @Entity marca esta classe como uma entidade JPA, o que significa que ela será mapeada para uma tabela no banco de dados.
 * A anotação @Table especifica o nome da tabela no banco de dados que esta entidade será mapeada.
 */
@Entity
@Table(name = "orders")
public class Order {

    /**
     * O campo 'id' é a chave primária da tabela 'orders'.
     * A anotação @Id marca este campo como a chave primária.
     * A anotação @GeneratedValue especifica que o valor do ID será gerado automaticamente pelo banco de dados.
     * A estratégia GenerationType.IDENTITY indica que o banco de dados irá incrementar o valor do ID a cada novo registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * O campo 'paymentMethod' armazena o método de pagamento escolhido para o pedido.
     */
    private String paymentMethod;

    private String donationNgo;

    /**
     * Obtém o ID do pedido.
     * @return O ID do pedido.
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID do pedido.
     * @param id O ID do pedido.
     */
    public void setId(Long id) {
        this.id = id;
    }

     /**
     * Obtém o usuário associado ao pedido.
     * @return O usuário associado ao pedido.
     */
    public User getUser() {
        return user;
    }

    /**
     * Define o usuário associado ao pedido.
     * @param user O usuário associado ao pedido.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Obtém o método de pagamento do pedido.
     * @return O método de pagamento do pedido.
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }


    /**
     * Define o método de pagamento do pedido.
     * @param paymentMethod O método de pagamento do pedido.
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDonationNgo() {
        return donationNgo;
    }

    public void setDonationNgo(String donationNgo) {
        this.donationNgo = donationNgo;
    }
}
