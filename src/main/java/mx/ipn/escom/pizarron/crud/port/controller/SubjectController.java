package mx.ipn.escom.pizarron.crud.port.controller;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.SubjectRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.DataRequest;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.simple.DataResponse;
import mx.ipn.escom.pizarron.crud.adapter.entity.SubjectEntity;
import mx.ipn.escom.pizarron.crud.domain.service.SubjectService;
import mx.ipn.escom.pizarron.crud.util.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subjects")
@Log4j2
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @PostMapping
    public ResponseEntity<DataResponse<SubjectEntity>> createSubject(@RequestBody SubjectRequestDto subjectRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(subjectService.createSubject(subjectRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{key}")
    public ResponseEntity<DataResponse<SubjectEntity>> updateSubject(@PathVariable(value="key") String key,
                                                                     @RequestBody SubjectRequestDto subjectRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(subjectService.updateSubject(key, subjectRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{key}")
    public ResponseEntity<DataResponse<SubjectEntity>> getSubject(@PathVariable(value="key") String key) {
        try{
            return new ResponseEntity<>(new DataResponse<>(subjectService.getSubjectByKey(key)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<DataResponse<Page<SubjectEntity>>> getListSubject(@RequestBody DataRequest<SubjectRequestDto> subjectRequestDto){
        try{
            return new ResponseEntity<>(new DataResponse<>(subjectService.getSubjects(subjectRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }
}
