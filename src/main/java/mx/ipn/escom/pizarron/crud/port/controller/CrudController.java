package mx.ipn.escom.pizarron.crud.port.controller;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.GroupRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.simple.DataResponse;
import mx.ipn.escom.pizarron.crud.adapter.entity.GroupEntity;
import mx.ipn.escom.pizarron.crud.domain.service.CatalogService;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
@Log4j2
public class CrudController {

    @Autowired
    CatalogService catalogService;

    @GetMapping("/group")
    public ResponseEntity<DataResponse<GroupEntity>> getInfo(@RequestBody GroupRequestDto groupRequestDto) {
        GroupEntity groupEntity = null;
        try{
            groupEntity = catalogService.getGroup(groupRequestDto);
        }catch (BusinessException businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    businessException.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(groupEntity), HttpStatus.OK);
    }


}
