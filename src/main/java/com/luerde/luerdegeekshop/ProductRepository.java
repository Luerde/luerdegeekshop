package com.luerde.luerdegeekshop;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * A interface ProductRepository estende JpaRepository, fornecendo uma maneira de interagir com o banco de dados
 * para a entidade Product. JpaRepository é uma interface do Spring Data JPA que fornece métodos CRUD (Create, Read,
 * Update, Delete) prontos para uso, bem como a capacidade de criar consultas personalizadas.
 *
 * @param <Product> A entidade que este repositório gerencia.
 * @param <Long> O tipo da chave primária da entidade.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Busca produtos onde o campo 'featured' é igual ao valor fornecido.
     * O Spring Data JPA cria automaticamente a implementação deste método com base no nome do método.
     * @param featured O valor booleano para o campo 'featured'.
     * @return Uma lista de produtos que correspondem ao critério.
     */
    List<Product> findByFeatured(boolean featured);

    /**
     * Busca produtos onde o campo 'onSale' é igual ao valor fornecido.
     * O Spring Data JPA cria automaticamente a implementação deste método com base no nome do método.
     * @param onSale O valor booleano para o campo 'onSale'.
     * @return Uma lista de produtos que correspondem ao critério.
     */
    List<Product> findByOnSale(boolean onSale);
}