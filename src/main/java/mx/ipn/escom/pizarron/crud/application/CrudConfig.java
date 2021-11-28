package mx.ipn.escom.pizarron.crud.application;

import mx.ipn.escom.pizarron.crud.domain.api.CrudServicePort;
import mx.ipn.escom.pizarron.crud.domain.api.service.CrudServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CrudConfig {

    @Bean
    public CrudServicePort activityServicePort() {
        return new CrudServiceImpl();
    }
}
