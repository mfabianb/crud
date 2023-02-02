package mx.ipn.escom.pizarron.crud.domain.impl;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.PermissionRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.SimpleRequest;
import mx.ipn.escom.pizarron.crud.adapter.entity.PermissionEntity;
import mx.ipn.escom.pizarron.crud.domain.service.PermissionService;
import mx.ipn.escom.pizarron.crud.port.repository.PermissionRepository;
import mx.ipn.escom.pizarron.crud.util.CreatePageable;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Objects;

import static mx.ipn.escom.pizarron.crud.util.Constants.*;

@Service
@Log4j2
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public PermissionEntity createPermission(PermissionRequestDto permissionRequestDto) throws BusinessException {
        if(Objects.isNull(permissionRequestDto)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_USER_STATUS);

        if(permissionRequestDto.getName().isEmpty() || permissionRequestDto.getDescription().isEmpty())
            throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_USER_STATUS);

        validatePermissionNotExist(permissionRequestDto);

        return permissionRepository.save(PermissionEntity.builder()
                .name(permissionRequestDto.getName())
                .description(permissionRequestDto.getDescription())
                .build());
    }

    @Override
    public Page<PermissionEntity> getPermissionList(SimpleRequest simpleRequest) throws BusinessException {

        if(Objects.isNull(simpleRequest)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_RESOURCE);

        CreatePageable pageable = new CreatePageable(simpleRequest.getPage(), simpleRequest.getSize(),
                simpleRequest.getSort(), simpleRequest.getOrder());

        return permissionRepository.findAll(pageable.getPageable());

    }

    private void validatePermissionNotExist(PermissionRequestDto permissionRequestDto) throws BusinessException {
        if(Objects.nonNull(permissionRepository.findByName(permissionRequestDto.getName())))
            throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + THIS_USER_STATUS);
    }
}
