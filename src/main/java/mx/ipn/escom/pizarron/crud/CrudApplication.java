package mx.ipn.escom.pizarron.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages = "mx.ipn.escom.pizarron")
@EntityScan(basePackages = "mx.ipn.escom.pizarron")
@EnableJpaRepositories(basePackages = "mx.ipn.escom.pizarron")
@EnableAsync
@EnableScheduling
@EnableRetry
@SpringBootApplication
public class CrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

}
