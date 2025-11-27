package com.luerde.luerdegeekshop;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A interface OrderRepository estende JpaRepository, fornecendo uma maneira de interagir com o banco de dados
 * para a entidade Order. JpaRepository é uma interface do Spring Data JPA que fornece métodos CRUD (Create, Read,
 * Update, Delete) prontos para uso, bem como a capacidade de criar consultas personalizadas.
 *
 * @param <Order> A entidade que este repositório gerencia.
 * @param <Long> O tipo da chave primária da entidade.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
