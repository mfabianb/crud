package mx.ipn.escom.pizarron.crud.domain.impl;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.UserStatusRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.SimpleRequest;
import mx.ipn.escom.pizarron.crud.adapter.entity.UserStatusEntity;
import mx.ipn.escom.pizarron.crud.domain.service.UserStatusService;
import mx.ipn.escom.pizarron.crud.port.repository.UserStatusRepository;
import mx.ipn.escom.pizarron.crud.util.CreatePageable;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Objects;

import static mx.ipn.escom.pizarron.crud.util.Constants.*;

@Service
@Log4j2
public class UserStatusServiceImpl implements UserStatusService {

    @Autowired
    UserStatusRepository userStatusRepository;

    @Override
    public UserStatusEntity createUserStatus(UserStatusRequestDto userStatusRequestDto) throws BusinessException {
        if(Objects.isNull(userStatusRequestDto)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_USER_STATUS);

        if(userStatusRequestDto.getStatus().isEmpty() || userStatusRequestDto.getDescription().isEmpty())
            throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_USER_STATUS);

        validateUserStatusNotExist(userStatusRequestDto);

        return userStatusRepository.save(UserStatusEntity.builder()
                .status(userStatusRequestDto.getStatus())
                .description(userStatusRequestDto.getDescription())
                .build());
    }

    @Override
    public Page<UserStatusEntity> getUserStatusList(SimpleRequest simpleRequest) throws BusinessException {

        if(Objects.isNull(simpleRequest)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_RESOURCE);

        CreatePageable pageable = new CreatePageable(simpleRequest.getPage(), simpleRequest.getSize(),
                simpleRequest.getSort(), simpleRequest.getOrder());

        return userStatusRepository.findAll(pageable.getPageable());

    }

    private void validateUserStatusNotExist(UserStatusRequestDto userStatusRequestDto) throws BusinessException {
        if(Objects.nonNull(userStatusRepository.findByStatus(userStatusRequestDto.getStatus())))
            throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + THIS_USER_STATUS);
    }
}
