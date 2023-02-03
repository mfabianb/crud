package mx.ipn.escom.pizarron.crud.domain.service;

import mx.ipn.escom.pizarron.crud.adapter.dto.request.UserRolRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.SimpleRequest;
import mx.ipn.escom.pizarron.crud.adapter.entity.UserRolEntity;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.springframework.data.domain.Page;

public interface UserRolService {
    UserRolEntity createUserRol(UserRolRequestDto userRolRequestDto) throws BusinessException;

    Page<UserRolEntity> getUserRolList(SimpleRequest simpleRequest) throws BusinessException;
}
