package mx.ipn.escom.pizarron.crud.controller;

import mx.ipn.escom.pizarron.crud.domain.api.CrudServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CrudController {

    @Autowired
    private CrudServicePort crudServicePort;

    @GetMapping("/info")
    public ResponseEntity<String> activityRecordDetail() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        responseHeaders.set("Access-Control-Allow-Methods", "GET,POST,OPTIONS,DELETE,PUT");
        responseHeaders.set("Access-Control-Allow-Headers", "X-API-KEY, Origin, X-Requested-With, Content-Type, Accept, Access-Control-Request-Method");
        responseHeaders.set("Content-Type", "application/json");
        String sss = crudServicePort.saludar();
        return new ResponseEntity<String>(sss, responseHeaders, HttpStatus.ACCEPTED);
    }

}
