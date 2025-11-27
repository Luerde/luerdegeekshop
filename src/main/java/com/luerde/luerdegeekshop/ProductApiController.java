package com.luerde.luerdegeekshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para lidar com requisições relacionadas a produtos.
 * A anotação @RestController combina as anotações @Controller e @ResponseBody,
 * o que significa que os métodos deste controlador retornarão objetos JSON em vez de visualizações.
 * A anotação @RequestMapping("/api/products") mapeia todas as requisições que começam com /api/products para este controlador.
 */
@RestController
@RequestMapping("/api/products")
public class ProductApiController {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retorna uma lista de todos os produtos.
     * A anotação @GetMapping mapeia requisições HTTP GET para este método.
     * @return Uma lista de todos os produtos.
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retorna um produto pelo seu ID.
     * A anotação @GetMapping("/{id}") mapeia requisições HTTP GET para /api/products/{id} para este método.
     * A anotação @PathVariable extrai o valor do ID da URL.
     * @param id O ID do produto a ser recuperado.
     * @return Uma ResponseEntity contendo o produto, se encontrado, ou notFound se não encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retorna uma lista de produtos em destaque.
     * A anotação @GetMapping("/featured") mapeia requisições HTTP GET para /api/products/featured para este método.
     * @return Uma lista de produtos em destaque.
     */
    @GetMapping("/featured")
    public List<Product> getFeaturedProducts() {
        return productRepository.findByFeatured(true);
    }

    /**
     * Retorna uma lista de produtos em promoção.
     * A anotação @GetMapping("/sale") mapeia requisições HTTP GET para /api/products/sale para este método.
     * @return Uma lista de produtos em promoção.
     */
    @GetMapping("/sale")
    public List<Product> getSaleProducts() {
        return productRepository.findByOnSale(true);
    }
}