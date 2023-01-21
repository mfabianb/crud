package mx.ipn.escom.pizarron.crud.domain.impl;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.GroupRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.GroupsRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.SimpleRequest;
import mx.ipn.escom.pizarron.crud.adapter.entity.GroupEntity;
import mx.ipn.escom.pizarron.crud.domain.service.CatalogService;
import mx.ipn.escom.pizarron.crud.port.repository.GroupRepository;
import mx.ipn.escom.pizarron.crud.util.CreatePageable;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

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
        validateGroupRequestDto(groupRequestDto, CREATE_DATA_REQUEST_NULL + THIS_GROUP);

        if(groupRequestDto.getName().isEmpty()) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_GROUP);

        validateGroupNotExist(groupRequestDto);

        return groupRepository.save(GroupEntity.builder().name(groupRequestDto.getName()).enable(Boolean.TRUE).build());
    }

    @Override
    public List<GroupEntity> createGroups(GroupsRequestDto groupsRequestDto) throws BusinessException {
        if(groupsRequestDto.getGroupsList().isEmpty()) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_GROUP);

        List<GroupEntity> groupEntityList = new ArrayList<>();

        groupsRequestDto.getGroupsList().forEach(groupRequestDto -> {
            try{
                validateGroupRequestDto(groupRequestDto, CREATE_DATA_REQUEST_NULL + THIS_GROUP);
                if(groupRequestDto.getName().isEmpty()) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_GROUP);
                validateGroupNotExist(groupRequestDto);
                groupEntityList.add(GroupEntity.builder().name(groupRequestDto.getName()).enable(Boolean.TRUE).build());
            }catch(Exception e){
                log.info(e.getMessage());
            }
        });
        return groupRepository.saveAll(groupEntityList);
    }

    @Override
    public GroupEntity updateGroup(GroupRequestDto groupRequestDto) throws BusinessException {
        validateGroupRequestDto(groupRequestDto, MODIFY_DATA_REQUEST_NULL + THIS_GROUP);

        GroupEntity groupEntity = validateGroupExist(groupRequestDto);

        if(groupRequestDto.getName().isEmpty()) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_GROUP);

        groupEntity.setName(groupRequestDto.getName());
        log.info(groupRequestDto.getEnabled());
        groupEntity.setEnable(groupEntity.getEnable());

        return groupRepository.save(groupEntity);
    }

    @Override
    public GroupEntity getGroupByName(GroupRequestDto groupRequestDto) throws BusinessException {
        if(Objects.isNull(groupRequestDto)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_GROUP);
        return validateGroupExist(groupRequestDto);
    }

    @Override
    public GroupEntity getGroupByKey(String key) throws BusinessException {
        if(Objects.isNull(key)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_GROUP);
        return validateGroupExist(key);
    }

    @Override
    public Page<GroupEntity> getGroups(SimpleRequest groupRequestDto) throws BusinessException {
        if(Objects.isNull(groupRequestDto)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_GROUP);
        CreatePageable pageable = new CreatePageable(groupRequestDto.getPage(), groupRequestDto.getSize(),
                groupRequestDto.getSort(), groupRequestDto.getOrder());
        return groupRepository.findAllByEnable(Boolean.TRUE, pageable.getPageable());
    }

    private GroupEntity validateGroupExist(GroupRequestDto groupRequestDto) throws BusinessException {
        GroupEntity groupEntity = groupRepository.findByName(groupRequestDto.getName());
        if(Objects.isNull(groupEntity)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_GROUP);
        return groupEntity;
    }

    private GroupEntity validateGroupExist(String key) throws BusinessException {
        GroupEntity groupEntity = groupRepository.findByIdGroup(key);
        if(Objects.isNull(groupEntity)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_GROUP);
        return groupEntity;
    }

    private void validateGroupNotExist(GroupRequestDto groupRequestDto) throws BusinessException {
        if(Objects.nonNull(groupRepository.findByName(groupRequestDto.getName())))
            throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + THIS_GROUP);
    }

    private void validateGroupRequestDto(GroupRequestDto groupRequestDto, String message) throws BusinessException {
        if(Objects.isNull(groupRequestDto)
                || Objects.isNull(groupRequestDto.getName())
                || Objects.isNull(groupRequestDto.getEnabled())) throw new BusinessException(message);
    }

}
