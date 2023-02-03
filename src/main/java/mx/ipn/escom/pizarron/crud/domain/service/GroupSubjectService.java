package mx.ipn.escom.pizarron.crud.domain.service;

import mx.ipn.escom.pizarron.crud.adapter.dto.request.GroupSubjectRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.DataRequest;
import mx.ipn.escom.pizarron.crud.adapter.entity.GroupSubjectEntity;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.springframework.data.domain.Page;

public interface GroupSubjectService {
    GroupSubjectEntity createGroupSubject(GroupSubjectRequestDto groupSubjectRequestDto) throws BusinessException;

    GroupSubjectEntity updateGroupSubject(String key, GroupSubjectRequestDto groupSubjectRequestDto) throws BusinessException;

    GroupSubjectEntity getGroupSubjectByKey(String key) throws BusinessException;

    Page<GroupSubjectEntity> getGroupSubjectsList(DataRequest<GroupSubjectRequestDto> simpleRequest) throws BusinessException;
}
