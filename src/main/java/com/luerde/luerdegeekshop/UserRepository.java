package com.luerde.luerdegeekshop;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * A interface UserRepository estende JpaRepository, fornecendo uma maneira de interagir com o banco de dados
 * para a entidade User. JpaRepository é uma interface do Spring Data JPA que fornece métodos CRUD (Create, Read,
 * Update, Delete) prontos para uso, bem como a capacidade de criar consultas personalizadas.
 *
 * @param <User> A entidade que este repositório gerencia.
 * @param <Long> O tipo da chave primária da entidade.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Encontra um usuário pelo seu endereço de e-mail.
     * O Spring Data JPA cria automaticamente a implementação deste método com base no nome do método.
     * @param email O e-mail do usuário a ser pesquisado.
     * @return Um Optional contendo o usuário, se encontrado, ou um Optional vazio caso contrário.
     */
    Optional<User> findByEmail(String email);

    User findByUsername(String currentUserName);
}