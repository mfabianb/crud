package mx.ipn.escom.pizarron.crud.domain.impl;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.entity.UserEntity;
import mx.ipn.escom.pizarron.crud.domain.service.AuthorizationService;
import mx.ipn.escom.pizarron.crud.domain.service.EncodeService;
import mx.ipn.escom.pizarron.crud.domain.service.UserService;
import mx.ipn.escom.pizarron.crud.util.UtilServlet;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

import static mx.ipn.escom.pizarron.crud.util.Constants.UNAUTHORIZED;

@Service
@Log4j2
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    UserService userService;

    @Autowired
    EncodeService encodeService;

    @Override
    public UserEntity getAuth() throws BusinessException {
        String token = UtilServlet.getToken();

        if(token.isEmpty()) throw new BusinessException(UNAUTHORIZED);

        String [] tokenData = encodeService.decode(token).split(",");

        if(Objects.isNull(tokenData[0])) throw new BusinessException(UNAUTHORIZED);

        UserEntity userEntity = userService.getUser(tokenData[0]).getUser();

        if(userEntity.getIdUserStatus().getIdUserStatus() != 1 || Boolean.FALSE.equals(userEntity.getTokenEnable())
                || !userEntity.getToken().equals(token))
            throw new BusinessException(UNAUTHORIZED);

        log.info("Authorization successful");

        return userEntity;

    }

}
