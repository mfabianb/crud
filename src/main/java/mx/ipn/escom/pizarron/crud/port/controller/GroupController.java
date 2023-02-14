package mx.ipn.escom.pizarron.crud.port.controller;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.GroupRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.DataRequest;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.simple.DataResponse;
import mx.ipn.escom.pizarron.crud.adapter.entity.GroupEntity;
import mx.ipn.escom.pizarron.crud.domain.service.GroupService;
import mx.ipn.escom.pizarron.crud.util.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
@Log4j2
public class GroupController {

    @Autowired
    GroupService groupService;

    @PostMapping
    public ResponseEntity<DataResponse<GroupEntity>> createGroup(@RequestBody GroupRequestDto groupRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(groupService.createGroup(groupRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{key}")
    public ResponseEntity<DataResponse<GroupEntity>> updateGroup(@PathVariable(value="key") String key,
                                                                 @RequestBody GroupRequestDto groupRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(groupService.updateGroup(key, groupRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{key}")
    public ResponseEntity<DataResponse<GroupEntity>> getGroup(@PathVariable(value="key") String key) {
        try{
            return new ResponseEntity<>(new DataResponse<>(groupService.getGroupByKey(key)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<DataResponse<Page<GroupEntity>>> getListGroup(@RequestBody DataRequest<GroupRequestDto> groupRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(groupService.getGroups(groupRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            log.info(businessException);
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }
}
