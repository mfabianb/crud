package mx.ipn.escom.pizarron.crud.domain.service;

import mx.ipn.escom.pizarron.crud.adapter.dto.request.PermissionRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.SimpleRequest;
import mx.ipn.escom.pizarron.crud.adapter.entity.PermissionEntity;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.springframework.data.domain.Page;

public interface PermissionService {
    PermissionEntity createPermission(PermissionRequestDto permissionRequestDto) throws BusinessException;

    Page<PermissionEntity> getPermissionList(SimpleRequest simpleRequest) throws BusinessException;
}
