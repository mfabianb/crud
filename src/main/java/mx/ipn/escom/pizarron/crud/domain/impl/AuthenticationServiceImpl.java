package mx.ipn.escom.pizarron.crud.domain.impl;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.AuthenticationResponseDto;
import mx.ipn.escom.pizarron.crud.adapter.entity.UserEntity;
import mx.ipn.escom.pizarron.crud.domain.service.AuthenticationService;
import mx.ipn.escom.pizarron.crud.domain.service.EncodeService;
import mx.ipn.escom.pizarron.crud.domain.service.UserService;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Objects;

import static mx.ipn.escom.pizarron.crud.util.Constants.*;

@Service
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserService userService;

    @Autowired
    EncodeService encodeService;

    @Override
    public AuthenticationResponseDto login(String username, String password) throws BusinessException {
        if(Objects.isNull(username) || Objects.isNull(password))
            throw new BusinessException(UNAUTHORIZED);

        UserEntity userEntity = userService.getUserByCredentials(username, password);

        String loginData = userEntity.getIdUser() + "," +
                userEntity.getIdUserStatus().getIdUserStatus() + "," +
                userEntity.getIdUserRol().getIdUserRol();

        String encode = encodeService.encode(loginData);

        userEntity.setLastLogin(LocalDateTime.now());
        userEntity.setToken(encode);
        userEntity.setTokenEnable(Boolean.TRUE);

        userService.userUpdate(userEntity);

        return AuthenticationResponseDto.builder().token(encode).build();
    }

    @Override
    public void logout(String key) throws BusinessException {
        if(Objects.isNull(key)) throw new BusinessException(UNAUTHORIZED);

        UserEntity userEntity = userService.getUser(key).getUser();

        userEntity.setTokenEnable(Boolean.FALSE);

        userService.userUpdate(userEntity);
    }

}
