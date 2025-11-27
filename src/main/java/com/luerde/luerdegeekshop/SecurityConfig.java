package com.luerde.luerdegeekshop;

import com.luerde.luerdegeekshop.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

/**
 * Classe de configuração de segurança para a aplicação.
 * A anotação @Configuration indica que esta classe contém definições de beans do Spring.
 */
@Configuration
public class SecurityConfig {

    /**
     * Cria um bean para o BCryptPasswordEncoder, que é usado para codificar senhas.
     * @return Uma instância de BCryptPasswordEncoder.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Cria um bean para o DaoAuthenticationProvider, que é usado para autenticar um usuário com base em um UserDetailsService.
     * @param customUserDetailsService O serviço de detalhes do usuário personalizado a ser usado.
     * @param passwordEncoder O codificador de senha a ser usado.
     * @return Uma instância de DaoAuthenticationProvider.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(CustomUserDetailsService customUserDetailsService, BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    /**
     * Cria um bean para o AuthenticationManager, que é o principal componente de autenticação do Spring Security.
     * @param authenticationConfiguration A configuração de autenticação.
     * @return Uma instância de AuthenticationManager.
     * @throws Exception Se ocorrer um erro ao obter o AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configura a cadeia de filtros de segurança.
     * @param http O objeto HttpSecurity a ser configurado.
     * @return Uma instância de SecurityFilterChain.
     * @throws Exception Se ocorrer um erro ao construir a cadeia de filtros de segurança.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita a proteção CSRF.
                .csrf(AbstractHttpConfigurer::disable)
                // Configura as regras de autorização de requisições.
                .authorizeHttpRequests(auth -> auth
                        // Permite requisições GET para /checkout para todos.
                        .requestMatchers(HttpMethod.GET, "/checkout").authenticated()
                        // Exige autenticação para requisições POST para /checkout.
                        .requestMatchers(HttpMethod.POST, "/checkout").authenticated()
                        // Permite o acesso a várias páginas e recursos estáticos para todos.
                        .requestMatchers("/", "/index", "/login", "/forgot-password", "/reset-password", "/produtos/**", "/api/auth/**", "/css/**", "/js/**", "/images/**", "/api/products/**", "/api/products", "/carrinho", "/thankyou", "/register", "/sobre", "/contato", "/politica-de-privacidade").permitAll()
                        // Exige autenticação para qualquer outra requisição.
                        .anyRequest().authenticated()
                )
                // Configura a funcionalidade "lembrar-me".
                .rememberMe(remember -> remember
                        // Define uma chave secreta para a funcionalidade "lembrar-me".
                        .key("uniqueAndSecret")
                        // Define o tempo de validade do token "lembrar-me" para 7 dias.
                        .tokenValiditySeconds(60 * 60 * 24 * 7)
                )
                // Configura o formulário de login.
                .formLogin(form -> form
                        // Especifica a página de login personalizada.
                        .loginPage("/login")
                        // Redireciona para a página inicial após o login bem-sucedido.
                        .defaultSuccessUrl("/", true)
                        // Permite que todos acessem a página de login.
                        .permitAll()
                );
        return http.build();
    }
}
