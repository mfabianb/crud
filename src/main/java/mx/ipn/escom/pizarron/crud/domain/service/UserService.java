package mx.ipn.escom.pizarron.crud.domain.service;

import mx.ipn.escom.pizarron.crud.adapter.dto.request.UserPermissionRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.UserRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.DataRequest;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.UserResponseDto;
import mx.ipn.escom.pizarron.crud.adapter.entity.UserEntity;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.springframework.data.domain.Page;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto) throws BusinessException;

    UserResponseDto updateUser(String key, UserRequestDto userRequestDto) throws BusinessException;

    Page<UserEntity> getUserList(DataRequest<UserRequestDto> userRequestDto) throws BusinessException;

    UserResponseDto getUser(String key, UserRequestDto userRequestDto) throws BusinessException;

    UserResponseDto updatePermissions(String key, UserPermissionRequestDto userPermissionRequestDto) throws BusinessException;
}
