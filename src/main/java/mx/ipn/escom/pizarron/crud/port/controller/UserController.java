package mx.ipn.escom.pizarron.crud.port.controller;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.UserPermissionRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.UserRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.DataRequest;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.UserResponseDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.simple.DataResponse;
import mx.ipn.escom.pizarron.crud.adapter.entity.UserEntity;
import mx.ipn.escom.pizarron.crud.adapter.entity.UserPermissionEntity;
import mx.ipn.escom.pizarron.crud.domain.service.UserService;
import mx.ipn.escom.pizarron.crud.util.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<DataResponse<UserResponseDto>> createUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userEntity = null;
        try{
            userEntity = userService.createUser(userRequestDto);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(userEntity), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<DataResponse<UserResponseDto>> updateUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userEntity = null;
        try{
            userEntity = userService.updateUser(userRequestDto);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(userEntity), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<DataResponse<UserResponseDto>> getUser(@RequestBody UserRequestDto userRequestDto) {
        try{
            UserResponseDto userEntity = userService.getUser(userRequestDto);
            return new ResponseEntity<>(new DataResponse<>(userEntity), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<DataResponse<Page<UserEntity>>> getUserList(@RequestBody DataRequest<UserRequestDto> userRequestDto) {
        log.info(userRequestDto);
        try{
            Page<UserEntity> userEntity = userService.getUserList(userRequestDto);
            return new ResponseEntity<>(new DataResponse<>(userEntity), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/permissions")
    public ResponseEntity<DataResponse<UserResponseDto>> updatePermissions(@RequestBody UserPermissionRequestDto userPermissionRequestDto) {
        log.info(userPermissionRequestDto);
        UserResponseDto userPermission = null;
        try{
            userPermission = userService.updatePermissions(userPermissionRequestDto);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(userPermission), HttpStatus.OK);
    }
}
