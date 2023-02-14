package mx.ipn.escom.pizarron.crud.domain.service;

import mx.ipn.escom.pizarron.crud.adapter.entity.UserEntity;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;

public interface AuthorizationService {
    UserEntity getAuth() throws BusinessException;
}
