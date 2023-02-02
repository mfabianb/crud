package mx.ipn.escom.pizarron.crud.port.controller;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.SchoolCycleRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.DataRequest;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.simple.DataResponse;
import mx.ipn.escom.pizarron.crud.adapter.entity.SchoolCycleEntity;
import mx.ipn.escom.pizarron.crud.domain.service.SchoolCycleService;
import mx.ipn.escom.pizarron.crud.util.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/school-cycles")
@Log4j2
public class SchoolCycleController {

    @Autowired
    SchoolCycleService schoolCycleService;

    @PostMapping
    public ResponseEntity<DataResponse<SchoolCycleEntity>> createSchoolCycle(@RequestBody SchoolCycleRequestDto schoolCycleRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(schoolCycleService.createSchoolCycle(schoolCycleRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{key}")
    public ResponseEntity<DataResponse<SchoolCycleEntity>> updateSchoolCycle(@PathVariable(value="key") Integer key,
                                                                             @RequestBody SchoolCycleRequestDto schoolCycleRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(schoolCycleService.updateSchoolCycle(key, schoolCycleRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{key}")
    public ResponseEntity<DataResponse<SchoolCycleEntity>> getSchoolCycle(@PathVariable(value="key") Integer key) {
        try{
            return new ResponseEntity<>(new DataResponse<>(schoolCycleService.getSchoolCycleByKey(key)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<DataResponse<Page<SchoolCycleEntity>>> getListSchoolCycle(@RequestBody DataRequest<SchoolCycleRequestDto> schoolCycleRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(schoolCycleService.getSchoolCycles(schoolCycleRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }
}
