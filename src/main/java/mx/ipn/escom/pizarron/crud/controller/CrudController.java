package mx.ipn.escom.pizarron.crud.controller;

import mx.ipn.escom.pizarron.crud.domain.api.CrudServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CrudController {

    @Autowired
    private CrudServicePort crudServicePort;

    @GetMapping("/info")
    public String activityRecordDetail() {
        return crudServicePort.saludar();
    }

}
