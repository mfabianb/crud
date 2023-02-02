package mx.ipn.escom.pizarron.crud.port.controller;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.ParticipantRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.simple.DataResponse;
import mx.ipn.escom.pizarron.crud.adapter.entity.ParticipantEntity;
import mx.ipn.escom.pizarron.crud.domain.service.ClassService;
import mx.ipn.escom.pizarron.crud.domain.service.ParticipantService;
import mx.ipn.escom.pizarron.crud.util.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/classes")
@Log4j2
public class ClassController {

    @Autowired
    ClassService classService;

    @Autowired
    ParticipantService participantService;

    @PostMapping
    public ResponseEntity<DataResponse<ParticipantEntity>> createGroup(@RequestBody ParticipantRequestDto participantRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(null), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

}
