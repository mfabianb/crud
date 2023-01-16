package mx.ipn.escom.pizarron.crud.application;

import mx.ipn.escom.pizarron.crud.domain.impl.CatalogServiceImpl;
import mx.ipn.escom.pizarron.crud.domain.service.CatalogService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CrudConfig {

    @Bean
    public CatalogService catalogServicePort() {
        return new CatalogServiceImpl();
    }
}