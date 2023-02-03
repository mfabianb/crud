package mx.ipn.escom.pizarron.crud.domain.service;

import mx.ipn.escom.pizarron.crud.adapter.dto.request.UserStatusRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.SimpleRequest;
import mx.ipn.escom.pizarron.crud.adapter.entity.UserStatusEntity;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.springframework.data.domain.Page;

public interface UserStatusService {
    UserStatusEntity createUserStatus(UserStatusRequestDto userStatusRequestDto) throws BusinessException;

    Page<UserStatusEntity> getUserStatusList(SimpleRequest simpleRequest) throws BusinessException;
}
