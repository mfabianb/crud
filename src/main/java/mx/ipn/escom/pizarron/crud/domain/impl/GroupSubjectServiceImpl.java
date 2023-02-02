package mx.ipn.escom.pizarron.crud.domain.impl;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.GroupSubjectRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.DataRequest;
import mx.ipn.escom.pizarron.crud.adapter.entity.GroupEntity;
import mx.ipn.escom.pizarron.crud.adapter.entity.GroupSubjectEntity;
import mx.ipn.escom.pizarron.crud.adapter.entity.SchoolCycleEntity;
import mx.ipn.escom.pizarron.crud.adapter.entity.SubjectEntity;
import mx.ipn.escom.pizarron.crud.domain.service.GroupService;
import mx.ipn.escom.pizarron.crud.domain.service.GroupSubjectService;
import mx.ipn.escom.pizarron.crud.domain.service.SchoolCycleService;
import mx.ipn.escom.pizarron.crud.domain.service.SubjectService;
import mx.ipn.escom.pizarron.crud.port.repository.GroupSubjectRepository;
import mx.ipn.escom.pizarron.crud.util.CreatePageable;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;

import java.util.Objects;

import static mx.ipn.escom.pizarron.crud.util.Constants.*;

@Service
@Log4j2
public class GroupSubjectServiceImpl implements GroupSubjectService {

    @Value("${crud.current-cycle}")
    String currentSchoolCycle;

    @Autowired
    GroupSubjectRepository groupSubjectRepository;

    @Autowired
    GroupService groupService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    SchoolCycleService schoolCycleService;

    @Override
    public GroupSubjectEntity createGroupSubject(GroupSubjectRequestDto groupSubjectRequestDto) throws BusinessException {

        GroupEntity groupEntity = groupService.getGroupByKey(groupSubjectRequestDto.getIdGroup());
        SubjectEntity subjectEntity = subjectService.getSubjectByKey(groupSubjectRequestDto.getIdSubject());
        SchoolCycleEntity schoolCycleEntity = schoolCycleService.getSchoolCycleByKey(groupSubjectRequestDto.getIdSchoolCycle());

        GroupSubjectEntity groupSubjectEntity;

        if (Objects.isNull(groupSubjectRepository.findByIdGroupAndIdSubjectAndIdSchoolCycle(groupEntity,
                subjectEntity, schoolCycleEntity))) {
            groupSubjectEntity = groupSubjectRepository.save(GroupSubjectEntity.builder()
                    .idGroup(groupEntity)
                    .idSubject(subjectEntity)
                    .idSchoolCycle(schoolCycleEntity)
                    .enable(Boolean.TRUE)
                    .build());
        } else {
            throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + THIS_RESOURCE);
        }

        return groupSubjectEntity;

    }

    @Override
    public GroupSubjectEntity updateGroupSubject(String key, GroupSubjectRequestDto groupSubjectRequestDto) throws BusinessException {

        if(Objects.isNull(groupSubjectRequestDto) || Objects.isNull(key))
            throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_RESOURCE);

        GroupSubjectEntity groupSubjectEntity = groupSubjectRepository.findByIdGroupSubject(key);

        GroupEntity groupEntity = groupService.getGroupByKey(groupSubjectRequestDto.getIdGroup());
        SubjectEntity subjectEntity = subjectService.getSubjectByKey(groupSubjectRequestDto.getIdSubject());
        SchoolCycleEntity schoolCycleEntity = schoolCycleService.getSchoolCycleByKey(groupSubjectRequestDto.getIdSchoolCycle());

        GroupSubjectEntity groupSubjectEntity1 = groupSubjectRepository.findByIdGroupAndIdSubjectAndIdSchoolCycle(groupEntity,
                subjectEntity, schoolCycleEntity);

        if(Objects.nonNull(groupSubjectEntity1) && groupSubjectEntity1.getEnable().equals(groupSubjectRequestDto.getEnable()))
            throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + THIS_RESOURCE);

        if(Objects.isNull(groupSubjectEntity)){
            throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_RESOURCE);
        }

        groupSubjectEntity.setIdSubject(subjectEntity);
        groupSubjectEntity.setIdGroup(groupEntity);
        groupSubjectEntity.setIdSchoolCycle(schoolCycleEntity);
        groupSubjectEntity.setEnable(groupSubjectRequestDto.getEnable());

        groupSubjectRepository.save(groupSubjectEntity);

        return groupSubjectEntity;

    }

    @Override
    public GroupSubjectEntity getGroupSubjectByKey(String key) throws BusinessException {

        if(Objects.isNull(key)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_RESOURCE);

        GroupSubjectEntity groupSubjectEntity = groupSubjectRepository.findByIdGroupSubject(key);

        if(Objects.isNull(groupSubjectEntity)){
            throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_RESOURCE);
        }

        return groupSubjectEntity;

    }

    @Override
    public Page<GroupSubjectEntity> getGroupSubjectsList(DataRequest<GroupSubjectRequestDto> simpleRequest) throws BusinessException {

        if(Objects.isNull(simpleRequest)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_RESOURCE);

        CreatePageable pageable = new CreatePageable(simpleRequest.getPage(), simpleRequest.getSize(),
                simpleRequest.getSort(), simpleRequest.getOrder());

        SchoolCycleEntity schoolCycleEntity = SchoolCycleEntity.builder().idSchoolCycle(Integer.parseInt(currentSchoolCycle)).build();

        Page<GroupSubjectEntity> groupSubjectEntityList = groupSubjectRepository.findAllByFilter(simpleRequest.getData().getGroupName(),
                simpleRequest.getData().getSubjectName(), schoolCycleEntity, simpleRequest.getData().getEnable(), pageable.getPageable());

        if(Objects.isNull(groupSubjectEntityList))
            throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_RESOURCE);

        return groupSubjectEntityList;

    }
}
