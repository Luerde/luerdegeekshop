package com.luerde.luerdegeekshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A anotação @SpringBootApplication é uma conveniência que adiciona todas as seguintes anotações:
 * <ul>
 *     <li>@Configuration: Marca a classe como uma fonte de definições de beans para o contexto da aplicação.</li>
 *     <li>@EnableAutoConfiguration: Diz ao Spring Boot para começar a adicionar beans com base nas configurações do classpath, outros beans e várias configurações de propriedade.</li>
 *     <li>@ComponentScan: Diz ao Spring para procurar por outros componentes, configurações e serviços no pacote 'com.luerde.luerdegeekshop', permitindo que ele encontre os controllers.</li>
 * </ul>
 */
@SpringBootApplication
public class LuerdegeekshopApplication {

	/**
	 * O método main utiliza o método estático 'run' do SpringApplication para iniciar a aplicação.
	 * O SpringApplication criará um ApplicationContext e registrará todos os beans que estão no classpath da aplicação.
	 * @param args Argumentos de linha de comando que podem ser passados para a aplicação.
	 */
	public static void main(String[] args) {
		SpringApplication.run(LuerdegeekshopApplication.class, args);
	}

}
