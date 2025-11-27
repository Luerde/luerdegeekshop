package com.luerde.luerdegeekshop;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Teste de integração para verificar se o carrinho é limpo após o checkout.
 * A anotação @SpringBootTest inicia o contexto do Spring Boot para o teste.
 * webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT inicia o servidor em uma porta aleatória.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartClearingTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait wait;

    /**
     * Configura o ambiente de teste antes de cada teste.
     * A anotação @BeforeEach indica que este método será executado antes de cada método de teste.
     */
    @BeforeEach
    public void setUp() {
        // Configura o WebDriver para o Chrome.
        WebDriverManager.chromedriver().setup();
        // Inicializa o driver do Chrome.
        driver = new ChromeDriver();
        // Inicializa o WebDriverWait com um tempo de espera de 10 segundos.
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Limpa o ambiente de teste após cada teste.
     * A anotação @AfterEach indica que este método será executado após cada método de teste.
     */
    @AfterEach
    public void tearDown() {
        // Fecha o navegador se o driver não for nulo.
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Testa se o carrinho é limpo após o checkout.
     * A anotação @Test marca este método como um método de teste.
     */
    @Test
    public void testCartIsClearedAfterCheckout() {
        // Navega para a página de produtos.
        driver.get("http://localhost:" + port + "/produtos");

        // Adiciona um produto ao carrinho.
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("add-to-cart-btn"))).click();

        // Vai para a página do carrinho.
        driver.get("http://localhost:" + port + "/carrinho");

        // Verifica se o carrinho não está vazio.
        WebElement cartItems = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cart-items")));
        assertTrue(cartItems.getText().contains("Quantidade: 1"));

        // Clica no botão de checkout.
        driver.findElement(By.id("checkout-button")).click();

        // Na página de checkout, preenche o formulário e envia.
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email"))).sendKeys("test@example.com");
        driver.findElement(By.id("payment-card")).click();
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Espera pela página de agradecimento.
        wait.until(ExpectedConditions.urlContains("/thankyou"));

        // Volta para a página do carrinho.
        driver.get("http://localhost:" + port + "/carrinho");

        // Verifica se o carrinho está vazio.
        WebElement emptyCartMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cart-items")));
        assertEquals("Seu carrinho está vazio.", emptyCartMessage.getText());
    }
}
