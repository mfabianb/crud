package mx.ipn.escom.pizarron.crud.domain.impl;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.UserRolRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.SimpleRequest;
import mx.ipn.escom.pizarron.crud.adapter.entity.UserRolEntity;
import mx.ipn.escom.pizarron.crud.domain.service.UserRolService;
import mx.ipn.escom.pizarron.crud.port.repository.UserRolRepository;
import mx.ipn.escom.pizarron.crud.util.CreatePageable;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Objects;

import static mx.ipn.escom.pizarron.crud.util.Constants.*;

@Service
@Log4j2
public class UserRolServiceImpl implements UserRolService {

    @Autowired
    UserRolRepository userRolRepository;

    @Override
    public UserRolEntity createUserRol(UserRolRequestDto userRolRequestDto) throws BusinessException {
        if(Objects.isNull(userRolRequestDto)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_USER_STATUS);

        if(userRolRequestDto.getRol().isEmpty() || userRolRequestDto.getDescription().isEmpty())
            throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_USER_STATUS);

        validateUserRolNotExist(userRolRequestDto);

        return userRolRepository.save(UserRolEntity.builder()
                .rol(userRolRequestDto.getRol())
                .description(userRolRequestDto.getDescription())
                .build());
    }

    @Override
    public Page<UserRolEntity> getUserRolList(SimpleRequest simpleRequest) throws BusinessException {

        if(Objects.isNull(simpleRequest)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_RESOURCE);

        CreatePageable pageable = new CreatePageable(simpleRequest.getPage(), simpleRequest.getSize(),
                simpleRequest.getSort(), simpleRequest.getOrder());

        return userRolRepository.findAll(pageable.getPageable());

    }

    private void validateUserRolNotExist(UserRolRequestDto userRolRequestDto) throws BusinessException {
        if(Objects.nonNull(userRolRepository.findByRol(userRolRequestDto.getRol())))
            throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + THIS_USER_STATUS);
    }
}
