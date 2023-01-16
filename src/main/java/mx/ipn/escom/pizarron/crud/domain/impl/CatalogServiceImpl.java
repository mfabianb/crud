package mx.ipn.escom.pizarron.crud.domain.impl;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.GroupRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.GroupsRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.entity.GroupEntity;
import mx.ipn.escom.pizarron.crud.domain.service.CatalogService;
import mx.ipn.escom.pizarron.crud.port.repository.GroupRepository;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static mx.ipn.escom.pizarron.crud.util.Constants.*;

@Service
@Log4j2
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    GroupRepository groupRepository;

    @PostConstruct
    public void init(){
        log.debug("Init service {}", this.getClass().getName());
    }

    @Override
    public GroupEntity createGroup(GroupRequestDto groupRequestDto) throws BusinessException {
        if(Objects.isNull(groupRequestDto)) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_GROUP);
        return groupRepository.save(GroupEntity.builder().name(groupRequestDto.getName()).enable(Boolean.TRUE).build());
    }

    @Override
    public List<GroupEntity> createGroups(GroupsRequestDto groupsRequestDto) throws BusinessException {
        if(groupsRequestDto.getGroupsList().isEmpty()) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_GROUP);
        List<GroupEntity> groupEntityList = new ArrayList<>();
        groupsRequestDto.getGroupsList().forEach(groupRequestDto ->
                groupEntityList.add(GroupEntity.builder().name(groupRequestDto.getName()).enable(Boolean.TRUE).build()));
        return groupRepository.saveAll(groupEntityList);
    }

    @Override
    public GroupEntity updateGroup(GroupRequestDto groupRequestDto) throws BusinessException {
        if(Objects.isNull(groupRequestDto)) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_GROUP);
        return groupRepository.save(GroupEntity.builder().name(groupRequestDto.getName()).enable(Boolean.TRUE).build());
    }

    @Override
    public GroupEntity getGroup(GroupRequestDto groupRequestDto) throws BusinessException {
        if(Objects.isNull(groupRequestDto)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_GROUP);
        return groupRepository.findByName(groupRequestDto.getName());
    }


}
