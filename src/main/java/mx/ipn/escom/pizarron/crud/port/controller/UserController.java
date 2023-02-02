package mx.ipn.escom.pizarron.crud.port.controller;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.UserPermissionRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.UserRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.DataRequest;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.UserResponseDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.simple.DataResponse;
import mx.ipn.escom.pizarron.crud.adapter.entity.UserEntity;
import mx.ipn.escom.pizarron.crud.domain.service.UserService;
import mx.ipn.escom.pizarron.crud.util.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<DataResponse<UserResponseDto>> createUser(@RequestBody UserRequestDto userRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(userService.createUser(userRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{key}")
    public ResponseEntity<DataResponse<UserResponseDto>> updateUser(@PathVariable(value="key") String key,
                                                                    @RequestBody UserRequestDto userRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(userService.updateUser(key, userRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{key}")
    public ResponseEntity<DataResponse<UserResponseDto>> getUser(@PathVariable(value="key") String key,
                                                                 @RequestBody UserRequestDto userRequestDto) {
        try{
            UserResponseDto userEntity = userService.getUser(key, userRequestDto);
            return new ResponseEntity<>(new DataResponse<>(userEntity), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<DataResponse<Page<UserEntity>>> getUserList(@RequestBody DataRequest<UserRequestDto> userRequestDto) {
        try{
            Page<UserEntity> userEntity = userService.getUserList(userRequestDto);
            return new ResponseEntity<>(new DataResponse<>(userEntity), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/permissions/{key}")
    public ResponseEntity<DataResponse<UserResponseDto>> updatePermissions(@PathVariable(value="key") String key,
                                                                           @RequestBody UserPermissionRequestDto userPermissionRequestDto) {
        try{
            return new ResponseEntity<>(new DataResponse<>(userService.updatePermissions(key, userPermissionRequestDto)), HttpStatus.OK);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }
}
