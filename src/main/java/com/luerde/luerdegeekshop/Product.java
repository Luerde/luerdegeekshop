package com.luerde.luerdegeekshop;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 * Representa um produto na loja.
 * A anotação @Entity marca esta classe como uma entidade JPA, o que significa que ela será mapeada para uma tabela no banco de dados.
 * A anotação @Table especifica o nome da tabela no banco de dados que esta entidade será mapeada.
 */
@Entity
@Table(name = "products")
public class Product {

    /**
     * O campo 'id' é a chave primária da tabela 'products'.
     * A anotação @Id marca este campo como a chave primária.
     * A anotação @GeneratedValue especifica que o valor do ID será gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * O nome do produto.
     * A anotação @Column(nullable = false) indica que este campo não pode ser nulo no banco de dados.
     */
    @Column(nullable = false)
    private String name;

    /**
     * A descrição do produto.
     */
    private String description;

    /**
     * O preço do produto.
     * A anotação @Column(nullable = false) indica que este campo não pode ser nulo no banco de dados.
     */
    @Column(nullable = false)
    private BigDecimal price;

    /**
     * A URL da imagem do produto.
     */
    private String imageUrl;

    /**
     * Indica se o produto está em destaque.
     */
    private boolean featured = false;

    /**
     * Indica se o produto está em promoção.
     */
    private boolean onSale = false;

    /**
     * O preço do produto em promoção.
     */
    private BigDecimal salePrice;

    // Getters e Setters

    /**
     * Obtém o ID do produto.
     * @return O ID do produto.
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID do produto.
     * @param id O ID do produto.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém o nome do produto.
     * @return O nome do produto.
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do produto.
     * @param name O nome do produto.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtém a descrição do produto.
     * @return A descrição do produto.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define a descrição do produto.
     * @param description A descrição do produto.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtém o preço do produto.
     * @return O preço do produto.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Define o preço do produto.
     * @param price O preço do produto.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Obtém a URL da imagem do produto.
     * @return A URL da imagem do produto.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Define a URL da imagem do produto.
     * @param imageUrl A URL da imagem do produto.
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Verifica se o produto está em destaque.
     * @return true se o produto estiver em destaque, false caso contrário.
     */
    public boolean isFeatured() {
        return featured;
    }

    /**
     * Define se o produto está em destaque.
     * @param featured true se o produto estiver em destaque, false caso contrário.
     */
    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    /**
     * Verifica se o produto está em promoção.
     * @return true se o produto estiver em promoção, false caso contrário.
     */
    public boolean isOnSale() {
        return onSale;
    }

    /**
     * Define se o produto está em promoção.
     * @param onSale true se o produto estiver em promoção, false caso contrário.
     */
    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    /**
     * Obtém o preço do produto em promoção.
     * @return O preço do produto em promoção.
     */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /**
     * Define o preço do produto em promoção.
     * @param salePrice O preço do produto em promoção.
     */
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }
}