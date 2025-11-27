package com.luerde.luerdegeekshop.service;

import com.luerde.luerdegeekshop.User;
import com.luerde.luerdegeekshop.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Serviço para carregar os detalhes do usuário do banco de dados.
 * A anotação @Service indica que esta classe é um serviço do Spring.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Carrega um usuário pelo seu nome de usuário (neste caso, o e-mail).
     * @param email O e-mail do usuário a ser carregado.
     * @return Um objeto UserDetails que representa o usuário.
     * @throws UsernameNotFoundException Se o usuário não for encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados pelo e-mail.
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Retorna um objeto UserDetails do Spring Security.
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>() // Por enquanto, não há papéis/autoridades.
        );
    }
}
