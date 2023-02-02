package mx.ipn.escom.pizarron.crud.port.controller;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.GroupSubjectRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.DataRequest;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.simple.DataResponse;
import mx.ipn.escom.pizarron.crud.adapter.entity.GroupSubjectEntity;
import mx.ipn.escom.pizarron.crud.domain.service.GroupSubjectService;
import mx.ipn.escom.pizarron.crud.util.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group-subjects")
@Log4j2
public class GroupSubjectController {

    @Autowired
    GroupSubjectService groupSubjectService;

    @PostMapping
    public ResponseEntity<DataResponse<GroupSubjectEntity>> createGroupSubject(@RequestBody GroupSubjectRequestDto groupSubjectRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(groupSubjectService.createGroupSubject(groupSubjectRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{key}")
    public ResponseEntity<DataResponse<GroupSubjectEntity>> updateGroupSubject(@PathVariable(value="key") String key,
                                                                               @RequestBody GroupSubjectRequestDto groupSubjectRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(groupSubjectService.updateGroupSubject(key, groupSubjectRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{key}")
    public ResponseEntity<DataResponse<GroupSubjectEntity>> getGroupSubject(@PathVariable(value="key") String key) {
        try{
            return new ResponseEntity<>(new DataResponse<>(groupSubjectService.getGroupSubjectByKey(key)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<DataResponse<Page<GroupSubjectEntity>>> getGroupSubjectList(@RequestBody DataRequest<GroupSubjectRequestDto> groupSubjectRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(groupSubjectService.getGroupSubjectsList(groupSubjectRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }
}
