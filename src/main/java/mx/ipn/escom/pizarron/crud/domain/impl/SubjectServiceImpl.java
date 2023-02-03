package mx.ipn.escom.pizarron.crud.domain.impl;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.SubjectRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.SubjectsRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.DataRequest;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.SimpleRequest;
import mx.ipn.escom.pizarron.crud.adapter.entity.SubjectEntity;
import mx.ipn.escom.pizarron.crud.domain.service.SubjectService;
import mx.ipn.escom.pizarron.crud.port.repository.SubjectRepository;
import mx.ipn.escom.pizarron.crud.util.CreatePageable;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static mx.ipn.escom.pizarron.crud.util.Constants.*;
import static mx.ipn.escom.pizarron.crud.util.Constants.THIS_SUBJECT;

@Service
@Log4j2
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    @Override
    public SubjectEntity createSubject(SubjectRequestDto subjectRequestDto) throws BusinessException {
        validateSubjectRequestDtoOnCreate(subjectRequestDto);

        if(subjectRequestDto.getName().isEmpty()) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_SUBJECT);

        validateSubjectNotExistOnCreate(subjectRequestDto);

        return subjectRepository.save(SubjectEntity.builder().name(subjectRequestDto.getName()).enable(subjectRequestDto.getEnable()).build());
    }

    @Override
    public List<SubjectEntity> createSubjects(SubjectsRequestDto subjectsRequestDto) throws BusinessException {
        if(subjectsRequestDto.getSubjectsList().isEmpty()) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_SUBJECT);

        List<SubjectEntity> subjectEntityList = new ArrayList<>();

        subjectsRequestDto.getSubjectsList().forEach(subjectRequestDto -> {
            try{
                validateSubjectRequestDtoOnCreate(subjectRequestDto);
                if(subjectRequestDto.getName().isEmpty()) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_SUBJECT);
                validateSubjectNotExistOnCreate(subjectRequestDto);
                subjectEntityList.add(SubjectEntity.builder().name(subjectRequestDto.getName()).enable(subjectRequestDto.getEnable()).build());
            }catch(Exception e){
                log.info(e.getMessage());
            }
        });
        return subjectRepository.saveAll(subjectEntityList);
    }

    @Override
    public SubjectEntity updateSubject(String key, SubjectRequestDto subjectRequestDto) throws BusinessException {
        validateSubjectRequestDtoOnUpdate(key, subjectRequestDto);

        SubjectEntity subjectEntity = validateSubjectExist(key);

        if(subjectRequestDto.getName().isEmpty()) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_SUBJECT);

        validateSubjectNotExistOnUpdate(subjectRequestDto);

        subjectEntity.setName(subjectRequestDto.getName());
        subjectEntity.setEnable(subjectRequestDto.getEnable());

        return subjectRepository.save(subjectEntity);
    }

    @Override
    public SubjectEntity getSubjectByName(SubjectRequestDto subjectRequestDto) throws BusinessException {
        if(Objects.isNull(subjectRequestDto)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_SUBJECT);
        return validateSubjectExist(subjectRequestDto);
    }

    @Override
    public SubjectEntity getSubjectByKey(String key) throws BusinessException {
        if(Objects.isNull(key)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_SUBJECT);
        return validateSubjectExist(key);
    }

    @Override
    public Page<SubjectEntity> getSubjects(DataRequest<SubjectRequestDto> subjectRequestDto) throws BusinessException {
        if(Objects.isNull(subjectRequestDto)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_SUBJECT);
        CreatePageable pageable = new CreatePageable(subjectRequestDto.getPage(), subjectRequestDto.getSize(),
                subjectRequestDto.getSort(), subjectRequestDto.getOrder());
        return subjectRepository.findAllByFilter(subjectRequestDto.getData().getName(), subjectRequestDto.getData().getEnable(),
                pageable.getPageable());
    }

    private SubjectEntity validateSubjectExist(SubjectRequestDto subjectRequestDto) throws BusinessException {
        SubjectEntity subjectEntity = subjectRepository.findByName(subjectRequestDto.getName());
        if(Objects.isNull(subjectEntity)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_SUBJECT);
        return subjectEntity;
    }

    private SubjectEntity validateSubjectExist(String key) throws BusinessException {
        SubjectEntity subjectEntity = subjectRepository.findByIdSubject(key);
        if(Objects.isNull(subjectEntity)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_SUBJECT);
        return subjectEntity;
    }

    private void validateSubjectNotExistOnCreate(SubjectRequestDto subjectRequestDto) throws BusinessException {
        if(Objects.nonNull(subjectRepository.findByName(subjectRequestDto.getName())))
            throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + THIS_SUBJECT);
    }

    private void validateSubjectNotExistOnUpdate(SubjectRequestDto subjectRequestDto) throws BusinessException {
        SubjectEntity subjectEntity = subjectRepository.findByName(subjectRequestDto.getName());
        if(Objects.nonNull(subjectEntity) && !subjectEntity.getIdSubject().equals(subjectRequestDto.getKey()))
            throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + THIS_SUBJECT);
    }

    private void validateSubjectRequestDtoOnCreate(SubjectRequestDto subjectRequestDto) throws BusinessException {
        if(Objects.isNull(subjectRequestDto)
                || Objects.isNull(subjectRequestDto.getName())
                || Objects.isNull(subjectRequestDto.getEnable())) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_SUBJECT);
    }

    private void validateSubjectRequestDtoOnUpdate(String key, SubjectRequestDto subjectRequestDto) throws BusinessException {
        if(Objects.isNull(subjectRequestDto)
                || Objects.isNull(key)
                || Objects.isNull(subjectRequestDto.getName())
                || Objects.isNull(subjectRequestDto.getEnable())) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_SUBJECT);
    }
}
