package mx.ipn.escom.pizarron.crud.domain.service;


import mx.ipn.escom.pizarron.crud.adapter.dto.response.AuthenticationResponseDto;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;

public interface AuthenticationService {
    AuthenticationResponseDto login(String username, String password) throws BusinessException;

    void logout(String key) throws BusinessException;
}
