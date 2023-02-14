package mx.ipn.escom.pizarron.crud.domain.impl;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.UserPermissionRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.UserRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.DataRequest;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.PermissionResponseDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.UserResponseDto;
import mx.ipn.escom.pizarron.crud.adapter.entity.*;
import mx.ipn.escom.pizarron.crud.domain.service.UserService;
import mx.ipn.escom.pizarron.crud.port.repository.PermissionRepository;
import mx.ipn.escom.pizarron.crud.port.repository.UserPermissionRepository;
import mx.ipn.escom.pizarron.crud.port.repository.UserRepository;
import mx.ipn.escom.pizarron.crud.util.CreatePageable;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static mx.ipn.escom.pizarron.crud.util.Constants.*;
import static mx.ipn.escom.pizarron.crud.util.Constants.THIS_USER;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserPermissionRepository userPermissionRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) throws BusinessException {
        validateUserDtoOnCreate(userRequestDto);

        validateUserNotExistOnCreate(userRequestDto);

        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .username(userRequestDto.getUsername())
                .password(userRequestDto.getPassword())
                .email(userRequestDto.getEmail())
                .name(userRequestDto.getName())
                .lastName(userRequestDto.getLastName())
                .secondLastName(userRequestDto.getSecondLastName())
                .idUserStatus(UserStatusEntity.builder().idUserStatus(userRequestDto.getIdUserStatus()).build())
                .idUserRol(UserRolEntity.builder().idUserRol(userRequestDto.getIdUserRol()).build())
                .build());

        createUserPermission(userEntity);

        return mapUserResponse(userEntity);
    }

    @Override
    public UserResponseDto updateUser(String key, UserRequestDto userRequestDto) throws BusinessException{

        validateUserDtoOnUpdate(key, userRequestDto);

        UserEntity userEntity = validateUserNotExistOnUpdate(key);

        Boolean hasChanges = validateUserDataOnUpdate(userRequestDto, userEntity);

        if (Boolean.TRUE.equals(hasChanges)) return mapUserResponse(userRepository.save(userEntity));
        else throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + "LA INFORMACIÓN DE " + THIS_USER);
    }

    @Override
    public UserEntity userUpdate(UserEntity userEntity){
        return userRepository.save(userEntity);
    }

    @Override
    public Page<UserEntity> getUserList(DataRequest<UserRequestDto> userRequestDto){
        CreatePageable pageable = new CreatePageable(userRequestDto.getPage(), userRequestDto.getSize(),
                userRequestDto.getSort(), userRequestDto.getOrder());
        return userRepository.findAllByFilter(userRequestDto.getData().getUsername(), userRequestDto.getData().getEmail(),
                userRequestDto.getData().getName(), userRequestDto.getData().getLastName(), userRequestDto.getData().getSecondLastName(),
                userRequestDto.getData().getLastLogin(),
                UserStatusEntity.builder().idUserStatus(userRequestDto.getData().getIdUserStatus()).build(),
                UserRolEntity.builder().idUserRol(userRequestDto.getData().getIdUserRol()).build(), pageable.getPageable());
    }

    @Override
    public UserResponseDto getUser(String key) throws BusinessException {
        if(Objects.isNull(key)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_USER);
        return mapUserResponse(validateUserNotExistOnUpdate(key));
    }

    @Override
    public UserResponseDto updatePermissions(String key, UserPermissionRequestDto userPermissionRequestDto) throws BusinessException {
        if(Objects.isNull(userPermissionRequestDto) || Objects.isNull(key))
            throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_USER);

        UserEntity userEntity = userRepository.findByIdUser(key);

        List<UserPermissionEntity> userPermissionEntityList = userPermissionRepository.findByIdUser(userEntity);

        if(userPermissionEntityList.isEmpty())
            throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_USER);

        userPermissionRequestDto.getPermissions().forEach(permissionDto ->
            userPermissionEntityList.stream()
                    .filter(userPermissionEntity -> userPermissionEntity.getIdPermission().getIdPermission().equals(permissionDto.getIdPermission()))
                    .forEach(userPermissionEntity -> userPermissionEntity.setEnable(permissionDto.getEnable()))
        );

        userPermissionEntityList.forEach(log::info);

        userPermissionRepository.saveAll(userPermissionEntityList);

        return mapUserResponse(userEntity);
    }

    @Override
    public String getFullName(UserEntity userEntity){
        return userEntity.getName() + " " + userEntity.getLastName() + " " + userEntity.getSecondLastName();
    }

    @Override
    public UserEntity getUserByCredentials(String username, String password) throws BusinessException {
        UserEntity userEntity = userRepository.findByUsernameAndPassword(username, password);
        if(Objects.isNull(userEntity) || userEntity.getIdUserStatus().getIdUserStatus() != 1)
            throw new BusinessException(UNAUTHORIZED);
        return userEntity;
    }

    private Boolean validateFullName(UserRequestDto userRequestDto, UserEntity userEntity){
        UserEntity userByName = userRepository.findByNameAndLastNameAndSecondLastName(userRequestDto.getName(),
                userRequestDto.getLastName(), userRequestDto.getSecondLastName());

        Boolean nameUpdate = Boolean.FALSE;
        Boolean lastNameUpdate = Boolean.FALSE;
        Boolean secondLastNameUpdate = Boolean.FALSE;

        if(Objects.isNull(userByName)){
            if(!userEntity.getName().toUpperCase(Locale.ROOT).equals(userRequestDto.getName().toUpperCase(Locale.ROOT))){
                userEntity.setName(userRequestDto.getName());
                nameUpdate = Boolean.TRUE;
            }

            if(!userEntity.getLastName().toUpperCase(Locale.ROOT).equals(userRequestDto.getLastName().toUpperCase(Locale.ROOT))) {
                userEntity.setLastName(userRequestDto.getLastName());
                lastNameUpdate = Boolean.TRUE;
            }

            if(!userEntity.getSecondLastName().toUpperCase(Locale.ROOT).equals(userRequestDto.getSecondLastName().toUpperCase(Locale.ROOT))) {
                userEntity.setSecondLastName(userRequestDto.getSecondLastName());
                secondLastNameUpdate = Boolean.TRUE;
            }
        }

        return nameUpdate || lastNameUpdate || secondLastNameUpdate;
    }

    private Boolean validateUserName(UserRequestDto userRequestDto, UserEntity userEntity){
        UserEntity userByUserName = userRepository.findByUsername(userRequestDto.getUsername());

        if(Objects.isNull(userByUserName) &&
                !userEntity.getUsername().toUpperCase(Locale.ROOT).equals(userRequestDto.getUsername().toUpperCase(Locale.ROOT))){
            userEntity.setUsername(userRequestDto.getUsername());
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private Boolean validateEmail(UserRequestDto userRequestDto, UserEntity userEntity){
        UserEntity userByEmail = userRepository.findByEmail(userRequestDto.getEmail());

        if(Objects.isNull(userByEmail) &&
                !userEntity.getEmail().toUpperCase(Locale.ROOT).equals(userRequestDto.getEmail().toUpperCase(Locale.ROOT))){
            userEntity.setEmail(userRequestDto.getEmail());
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private Boolean validateUserDataOnUpdate(UserRequestDto userRequestDto, UserEntity userEntity){

        Boolean validFullName = validateFullName(userRequestDto, userEntity);

        Boolean validUserName = validateUserName(userRequestDto, userEntity);

        Boolean validEmail = validateEmail(userRequestDto, userEntity);

        Boolean validPassword = Boolean.FALSE;

        Boolean validUserStatus = Boolean.FALSE;

        Boolean validUserRol = Boolean.FALSE;

        if(!userEntity.getPassword().toUpperCase(Locale.ROOT).equals(userRequestDto.getPassword().toUpperCase(Locale.ROOT))) {
            userEntity.setPassword(userRequestDto.getPassword());
            validPassword = Boolean.TRUE;
        }

        if(!userEntity.getIdUserStatus().getIdUserStatus().equals(userRequestDto.getIdUserStatus())) {
            userEntity.setIdUserStatus(UserStatusEntity.builder().idUserStatus(userRequestDto.getIdUserStatus()).build());
            validUserStatus = Boolean.TRUE;
        }

        if(!userEntity.getIdUserRol().getIdUserRol().equals(userRequestDto.getIdUserRol())) {
            userEntity.setIdUserRol(UserRolEntity.builder().idUserRol(userRequestDto.getIdUserRol()).build());
            validUserRol = Boolean.TRUE;
        }

        return validFullName || validUserName || validEmail || validPassword || validUserStatus || validUserRol;
    }

    private void validateUserDtoOnCreate(UserRequestDto userRequestDto) throws BusinessException {
        if(Objects.isNull(userRequestDto)
                || Objects.isNull(userRequestDto.getUsername()) || userRequestDto.getUsername().isEmpty()
                || Objects.isNull(userRequestDto.getPassword()) || userRequestDto.getPassword().isEmpty()
                || Objects.isNull(userRequestDto.getEmail()) || userRequestDto.getEmail().isEmpty()
                || Objects.isNull(userRequestDto.getName()) || userRequestDto.getName().isEmpty()
                || Objects.isNull(userRequestDto.getLastName()) || userRequestDto.getLastName().isEmpty()
                || Objects.isNull(userRequestDto.getSecondLastName()) || userRequestDto.getSecondLastName().isEmpty()
                || userRequestDto.getIdUserStatus() <= 0
                || userRequestDto.getIdUserRol() <= 0 ) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_USER);
    }

    private void validateUserDtoOnUpdate(String key, UserRequestDto userRequestDto) throws BusinessException {
        validateUserDtoOnCreate(userRequestDto);
        if(Objects.isNull(key))
            throw new BusinessException(MODIFY_DATA_REQUEST_NULL + THIS_USER);
    }

    private void validateUserNotExistOnCreate(UserRequestDto userRequestDto) throws BusinessException {
        if(Objects.nonNull(userRepository.findByNameAndLastNameAndSecondLastName(userRequestDto.getName(),
                userRequestDto.getLastName(), userRequestDto.getSecondLastName()))
                || Objects.nonNull(userRepository.findByUsername(userRequestDto.getUsername()))
                || Objects.nonNull(userRepository.findByEmail(userRequestDto.getEmail())))
            throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + THIS_USER);
    }

    private UserEntity validateUserNotExistOnUpdate(String key) throws BusinessException {
        UserEntity userEntity = userRepository.findByIdUser(key);
        if(Objects.isNull(userEntity))
            throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_USER);
        return userEntity;
    }

    private void createUserPermission(UserEntity userEntity){
        List<PermissionEntity> permissionEntityList = permissionRepository.findAll();
        List<UserPermissionEntity> userPermissionEntityList = new ArrayList<>();
        permissionEntityList.forEach(permissionEntity ->
            userPermissionEntityList.add(UserPermissionEntity.builder()
                    .idUser(userEntity)
                    .idPermission(permissionEntity)
                    .enable(Boolean.FALSE)
                    .build())
        );
        userPermissionRepository.saveAll(userPermissionEntityList);
    }

    private UserResponseDto mapUserResponse(UserEntity user){
        List<UserPermissionEntity> userPermissionList = userPermissionRepository.findByIdUser(user);

        UserResponseDto result = UserResponseDto.builder().user(user).build();
        List<PermissionResponseDto> permissionEntities = new ArrayList<>();

        userPermissionList.forEach(userPermission -> permissionEntities.add(PermissionResponseDto.builder()
                .idPermission(userPermission.getIdPermission().getIdPermission())
                .description(userPermission.getIdPermission().getDescription())
                .name(userPermission.getIdPermission().getName())
                .enable(userPermission.getEnable())
                .build()));

        result.setPermissions(permissionEntities);
        return result;
    }
}
