package mx.ipn.escom.pizarron.crud.domain.impl;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.ClassRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.entity.ClassEntity;
import mx.ipn.escom.pizarron.crud.adapter.entity.GroupSubjectEntity;
import mx.ipn.escom.pizarron.crud.domain.service.ClassService;
import mx.ipn.escom.pizarron.crud.domain.service.GroupSubjectService;
import mx.ipn.escom.pizarron.crud.port.repository.ClassRepository;
import mx.ipn.escom.pizarron.crud.util.GenerateRandom;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Objects;

import static mx.ipn.escom.pizarron.crud.util.Constants.*;

@Service
@Log4j2
public class ClassServiceImpl implements ClassService {

    @Autowired
    ClassRepository classRepository;

    @Autowired
    GroupSubjectService groupSubjectService;

    @Override
    public ClassEntity createClass(ClassRequestDto classRequestDto) throws BusinessException, NoSuchAlgorithmException {
        validateClassRequestOnCreate(classRequestDto);

        validateClassNotExists(classRequestDto);

        GroupSubjectEntity groupSubjectEntity = validateGroupSubjectExist(classRequestDto.getIdGroupSubject());

        return classRepository.save(ClassEntity.builder()
                .idGroupSubject(groupSubjectEntity)
                .creationDate(LocalDateTime.now())
                .enable(Boolean.FALSE)
                .enrollLink(GenerateRandom.getRandom()).build());
    }

    @Override
    public ClassEntity updateClass(String key, ClassRequestDto classRequestDto) throws BusinessException {

        validateClassRequestOnUpdate(key, classRequestDto);

        ClassEntity classEntity = validateClassExists(key);

        classEntity.setName(classRequestDto.getName());
        classEntity.setDescription(classRequestDto.getDescription());
        classEntity.setEnable(classRequestDto.getEnable());

        return classRepository.save(classEntity);
    }

    @Override
    public ClassEntity getClass(String key) throws BusinessException {
        if(Objects.isNull(key)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_CLASS);
        return validateClassExists(key);
    }

    @Override
    public String getFullName(ClassEntity classEntity){
        return classEntity.getIdGroupSubject().getIdGroup().getName() + " - "
                + classEntity.getIdGroupSubject().getIdSubject().getName();
    }

    private void validateClassRequestOnCreate(ClassRequestDto classRequestDto) throws BusinessException {
        if(Objects.isNull(classRequestDto) || Objects.isNull(classRequestDto.getIdGroupSubject())){
            throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_CLASS);
        }
    }

    private void validateClassRequestOnUpdate(String key, ClassRequestDto classRequestDto) throws BusinessException {
        validateClassRequestOnCreate(classRequestDto);
        if(Objects.isNull(key) || Objects.isNull(classRequestDto.getName())
                || Objects.isNull(classRequestDto.getDescription()) || Objects.isNull(classRequestDto.getEnable())){
            throw new BusinessException(MODIFY_DATA_REQUEST_NULL + THIS_CLASS);
        }
    }

    private void validateClassNotExists(ClassRequestDto classRequestDto) throws BusinessException {
        if(Objects.nonNull(classRepository.findByIdGroupSubject(classRequestDto.getIdGroupSubject()))){
            throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + THIS_CLASS);
        }
    }

    private ClassEntity validateClassExists(String key) throws BusinessException {
        ClassEntity classEntity = classRepository.findByIdClass(key);
        if(Objects.isNull(classEntity)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_CLASS);
        return classEntity;
    }

    private GroupSubjectEntity validateGroupSubjectExist(String key) throws BusinessException {
        GroupSubjectEntity groupSubjectEntity = groupSubjectService.getGroupSubjectByKey(key);
        if(Objects.isNull(groupSubjectEntity)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_CLASS);
        return groupSubjectEntity;
    }

}
