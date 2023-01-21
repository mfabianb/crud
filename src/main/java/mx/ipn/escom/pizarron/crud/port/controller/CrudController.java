package mx.ipn.escom.pizarron.crud.port.controller;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.GroupRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.SimpleRequest;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.simple.DataResponse;
import mx.ipn.escom.pizarron.crud.adapter.entity.GroupEntity;
import mx.ipn.escom.pizarron.crud.domain.service.CatalogService;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalog")
@Log4j2
public class CrudController {

    @Autowired
    CatalogService catalogService;

    @PostMapping("/group")
    public ResponseEntity<DataResponse<GroupEntity>> createGroup(@RequestBody GroupRequestDto groupRequestDto) {
        GroupEntity groupEntity = null;
        try{
            groupEntity = catalogService.createGroup(groupRequestDto);
        }catch (BusinessException businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    businessException.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(groupEntity), HttpStatus.OK);
    }

    @PutMapping("/group")
    public ResponseEntity<DataResponse<GroupEntity>> updateGroup(@RequestBody GroupRequestDto groupRequestDto) {
        GroupEntity groupEntity = null;
        try{
            groupEntity = catalogService.updateGroup(groupRequestDto);
        }catch (BusinessException businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    businessException.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(groupEntity), HttpStatus.OK);
    }

    @GetMapping("/group/{key}")
    public ResponseEntity<DataResponse<GroupEntity>> getGroup(@PathVariable(value="key") String key) {
        GroupEntity groupEntity = null;
        try{
            groupEntity = catalogService.getGroupByKey(key);
        }catch (BusinessException businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    businessException.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(groupEntity), HttpStatus.OK);
    }

    @GetMapping("/group")
    public ResponseEntity<DataResponse<GroupEntity>> getGroup(@RequestBody GroupRequestDto groupRequestDto) {
        GroupEntity groupEntity = null;
        try{
            groupEntity = catalogService.getGroupByName(groupRequestDto);
        }catch (BusinessException businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    businessException.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(groupEntity), HttpStatus.OK);
    }

    @GetMapping("/groups")
    public ResponseEntity<DataResponse<Page<GroupEntity>>> getListGroup(@RequestBody SimpleRequest groupRequestDto) {
        Page<GroupEntity> groupEntity = null;
        try{
            groupEntity = catalogService.getGroups(groupRequestDto);
        }catch (BusinessException businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    businessException.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(groupEntity), HttpStatus.OK);
    }


}
