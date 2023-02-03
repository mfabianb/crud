package mx.ipn.escom.pizarron.crud.domain.impl;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.GroupRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.GroupsRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.DataRequest;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.SimpleRequest;
import mx.ipn.escom.pizarron.crud.adapter.entity.GroupEntity;
import mx.ipn.escom.pizarron.crud.domain.service.GroupService;
import mx.ipn.escom.pizarron.crud.port.repository.GroupRepository;
import mx.ipn.escom.pizarron.crud.util.CreatePageable;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static mx.ipn.escom.pizarron.crud.util.Constants.*;
import static mx.ipn.escom.pizarron.crud.util.Constants.THIS_GROUP;

@Service
@Log4j2
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Override
    public GroupEntity createGroup(GroupRequestDto groupRequestDto) throws BusinessException {
        validateGroupRequestDtoOnCreate(groupRequestDto);

        if(groupRequestDto.getName().isEmpty()) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_GROUP);

        validateGroupNotExistOnCreate(groupRequestDto);

        return groupRepository.save(GroupEntity.builder().name(groupRequestDto.getName()).enable(groupRequestDto.getEnable()).build());
    }

    @Override
    public List<GroupEntity> createGroups(GroupsRequestDto groupsRequestDto) throws BusinessException {
        if(groupsRequestDto.getGroupsList().isEmpty()) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_GROUP);

        List<GroupEntity> groupEntityList = new ArrayList<>();

        groupsRequestDto.getGroupsList().forEach(groupRequestDto -> {
            try{
                validateGroupRequestDtoOnCreate(groupRequestDto);
                if(groupRequestDto.getName().isEmpty()) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_GROUP);
                validateGroupNotExistOnCreate(groupRequestDto);
                groupEntityList.add(GroupEntity.builder().name(groupRequestDto.getName()).enable(groupRequestDto.getEnable()).build());
            }catch(Exception e){
                log.info(e.getMessage());
            }
        });
        return groupRepository.saveAll(groupEntityList);
    }

    @Override
    public GroupEntity updateGroup(String key, GroupRequestDto groupRequestDto) throws BusinessException {
        validateGroupRequestDtoOnUpdate(key, groupRequestDto);

        GroupEntity groupEntity = validateGroupExist(key);

        if(groupRequestDto.getName().isEmpty()) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_GROUP);

        validateGroupNotExistOnUpdate(groupRequestDto);

        groupEntity.setName(groupRequestDto.getName());
        groupEntity.setEnable(groupRequestDto.getEnable());

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
    public Page<GroupEntity> getGroups(DataRequest<GroupRequestDto> groupRequestDto) throws BusinessException {
        if(Objects.isNull(groupRequestDto)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_GROUP);
        CreatePageable pageable = new CreatePageable(groupRequestDto.getPage(), groupRequestDto.getSize(),
                groupRequestDto.getSort(), groupRequestDto.getOrder());
        return groupRepository.findAllByFilter(groupRequestDto.getData().getName(), groupRequestDto.getData().getEnable(), pageable.getPageable());
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

    private void validateGroupNotExistOnCreate(GroupRequestDto groupRequestDto) throws BusinessException {
        if(Objects.nonNull(groupRepository.findByName(groupRequestDto.getName())))
            throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + THIS_GROUP);
    }

    private void validateGroupNotExistOnUpdate(GroupRequestDto groupRequestDto) throws BusinessException {
        GroupEntity groupEntity = groupRepository.findByName(groupRequestDto.getName());
        if(Objects.nonNull(groupEntity) && !groupEntity.getIdGroup().equals(groupRequestDto.getKey()))
            throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + THIS_GROUP);
    }

    private void validateGroupRequestDtoOnCreate(GroupRequestDto groupRequestDto) throws BusinessException {
        if(Objects.isNull(groupRequestDto)
                || Objects.isNull(groupRequestDto.getName())
                || Objects.isNull(groupRequestDto.getEnable())) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_GROUP);
    }

    private void validateGroupRequestDtoOnUpdate(String key, GroupRequestDto groupRequestDto) throws BusinessException {
        if(Objects.isNull(groupRequestDto)
                || Objects.isNull(key)
                || Objects.isNull(groupRequestDto.getName())
                || Objects.isNull(groupRequestDto.getEnable())) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_GROUP);
    }
}
