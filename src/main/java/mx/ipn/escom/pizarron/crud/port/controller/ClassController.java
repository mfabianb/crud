package mx.ipn.escom.pizarron.crud.port.controller;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.ClassRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.ClassResponseDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.simple.DataResponse;
import mx.ipn.escom.pizarron.crud.adapter.entity.ClassEntity;
import mx.ipn.escom.pizarron.crud.domain.service.ClassService;
import mx.ipn.escom.pizarron.crud.domain.service.ParticipantService;
import mx.ipn.escom.pizarron.crud.util.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
@Log4j2
public class ClassController {
    @Autowired
    ClassService classService;

    @Autowired
    ParticipantService participantService;

    @PostMapping
    public ResponseEntity<DataResponse<ClassEntity>> createClass(@RequestBody ClassRequestDto classRequestDto){
        try{
            return new ResponseEntity<>(new DataResponse<>(classService.createClass(classRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            log.info(businessException);
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{key}")
    public ResponseEntity<DataResponse<ClassEntity>> updateClass(@PathVariable(value="key") String key,
                                                                 @RequestBody ClassRequestDto classRequestDto){
        try{
            return new ResponseEntity<>(new DataResponse<>(classService.updateClass(key, classRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            log.info(businessException);
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{key}")
    public ResponseEntity<DataResponse<List<ClassResponseDto>>> getClassesByUer(@PathVariable(value="key") String key) {
        try{
            return new ResponseEntity<>(new DataResponse<>(participantService.getUserClasses(key)), HttpStatus.OK);
        }catch (Exception businessException){
            log.info(businessException);
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }
}
