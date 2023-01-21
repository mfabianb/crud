package mx.ipn.escom.pizarron.crud.domain.service;

import mx.ipn.escom.pizarron.crud.adapter.dto.request.GroupRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.GroupsRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.SimpleRequest;
import mx.ipn.escom.pizarron.crud.adapter.entity.GroupEntity;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatalogService {

    GroupEntity createGroup(GroupRequestDto groupRequestDto) throws BusinessException;

    List<GroupEntity> createGroups(GroupsRequestDto groupsRequestDto) throws BusinessException;

    GroupEntity updateGroup(GroupRequestDto groupRequestDto) throws BusinessException;

    GroupEntity getGroupByName(GroupRequestDto groupRequestDto) throws BusinessException;

    GroupEntity getGroupByKey(String key) throws BusinessException;

    Page<GroupEntity> getGroups(SimpleRequest groupRequestDto) throws BusinessException;
}
