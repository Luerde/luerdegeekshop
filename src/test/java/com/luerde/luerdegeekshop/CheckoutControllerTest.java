package com.luerde.luerdegeekshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Teste para o controlador de checkout.
 * A anotação @SpringBootTest inicia o contexto do Spring Boot para o teste.
 * A anotação @AutoConfigureMockMvc configura o MockMvc, que é usado para fazer requisições HTTP para o controlador.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CheckoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Testa o processo de checkout.
     * A anotação @Test marca este método como um método de teste.
     * @throws Exception Se ocorrer um erro ao executar o teste.
     */
    @Test
    public void testCheckout() throws Exception {
        // Executa uma requisição POST para /checkout com os parâmetros "payment-method" e "email".
        mockMvc.perform(post("/checkout")
                .param("payment-method", "card")
                .param("email", "test@example.com"))
                // Espera que o status da resposta seja um redirecionamento (3xx).
                .andExpect(status().is3xxRedirection())
                // Espera que a URL de redirecionamento seja /thankyou.
                .andExpect(redirectedUrl("/thankyou"));
    }
}