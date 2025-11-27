package com.luerde.luerdegeekshop.controller;

import com.luerde.luerdegeekshop.Order;
import com.luerde.luerdegeekshop.OrderRepository;
import com.luerde.luerdegeekshop.User;
import com.luerde.luerdegeekshop.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import com.luerde.luerdegeekshop.ProductRepository;
import com.luerde.luerdegeekshop.Product;

/**
 * Controlador para as páginas principais do site.
 * A anotação @Controller indica que esta classe é um controlador Spring MVC.
 */
@Controller
public class SiteController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Exibe a página inicial.
     * @return O nome da visualização da página inicial (index.html).
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Exibe a página de produtos.
     * @return O nome da visualização da página de produtos (produtos.html).
     */
    @GetMapping("/produtos")
    public String produtos() {
        return "produtos";
    }

    @GetMapping("/produtos/{id}")
    public String produtoDetalhe(@PathVariable("id") Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        return "produto-detalhe";
    }

    /**
     * Exibe a página do carrinho.
     * @return O nome da visualização da página do carrinho (carrinho.html).
     */
    @GetMapping("/carrinho")
    public String carrinho() {
        return "carrinho";
    }

    /**
     * Exibe a página de login.
     * @return O nome da visualização da página de login (login.html).
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Exibe a página sobre.
     * @return O nome da visualização da página sobre (sobre.html).
     */
    @GetMapping("/sobre")
    public String sobre() {
        return "sobre";
    }

    /**
     * Exibe a página de contato.
     * @return O nome da visualização da página de contato (contato.html).
     */
    @GetMapping("/contato")
    public String contato() {
        return "contato";
    }

    /**
     * Exibe a página de política de privacidade.
     * @return O nome da visualização da página de política de privacidade (politica-de-privacidade.html).
     */
    @GetMapping("/politica-de-privacidade")
    public String politicaDePrivacidade() {
        return "politica-de-privacidade";
    }

    /**
     * Processa o checkout. Se for um GET, exibe a página de checkout. Se for um POST, processa o pedido.
     * @param request O objeto HttpServletRequest.
     * @return O nome da visualização de checkout ou um redirecionamento para a página de agradecimento.
     */
    @RequestMapping(value = "/checkout", method = {RequestMethod.GET, RequestMethod.POST})
    public String checkout(HttpServletRequest request) {
        if (request.getMethod().equalsIgnoreCase("POST")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            User user = userRepository.findByUsername(currentUserName);

            // Cria um novo pedido.
            Order order = new Order();
            order.setUser(user);
            // Define o método de pagamento.
            order.setPaymentMethod(request.getParameter("payment-method"));
            // Define a ONG para doação.
            order.setDonationNgo(request.getParameter("ngo-donation"));
            // Salva o pedido no banco de dados.
            orderRepository.save(order);

            // Redireciona para a página de agradecimento.
            return "redirect:/thankyou";
        }
        // Se for um GET, exibe a página de checkout.
        return "checkout";
    }

    /**
     * Exibe a página de agradecimento.
     * @return O nome da visualização da página de agradecimento (thankyou.html).
     */
    @GetMapping("/thankyou")
    public String thankyou() {
        return "thankyou";
    }
}