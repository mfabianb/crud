package mx.ipn.escom.pizarron.crud.port.controller;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.ParticipantRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.DataRequest;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.simple.DataResponse;
import mx.ipn.escom.pizarron.crud.adapter.entity.ParticipantEntity;
import mx.ipn.escom.pizarron.crud.domain.service.ClassService;
import mx.ipn.escom.pizarron.crud.domain.service.ParticipantService;
import mx.ipn.escom.pizarron.crud.util.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participants")
@Log4j2
public class ParticipantsController {

    @Autowired
    ClassService classService;

    @Autowired
    ParticipantService participantService;

    @PostMapping("/professors")
    public ResponseEntity<DataResponse<ParticipantEntity>> enrollProfessor(@RequestBody ParticipantRequestDto participantRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(participantService.enrollProfessor(participantRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/students")
    public ResponseEntity<DataResponse<ParticipantEntity>> requestEnroll(@RequestBody ParticipantRequestDto participantRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(participantService.requestEnrollStudent(participantRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/students/{key}")
    public ResponseEntity<DataResponse<ParticipantEntity>> attendRequestEnroll(@PathVariable(value="key") String key,
                                                                                      @RequestBody ParticipantRequestDto participantRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(participantService.attendRequestEnrollmentStudent(key, participantRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/students")
    public ResponseEntity<DataResponse<List<ParticipantEntity>>> attendRequestAllEnroll(@RequestBody ParticipantRequestDto participantRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(participantService.attendAllRequestEnrollmentStudent(participantRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<DataResponse<Page<ParticipantEntity>>> getParticipants(@RequestBody DataRequest<ParticipantRequestDto> participantRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(participantService.getParticipants(participantRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

}
