package mx.ipn.escom.pizarron.crud.domain.impl;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.SchoolCycleRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.DataRequest;
import mx.ipn.escom.pizarron.crud.adapter.entity.SchoolCycleEntity;
import mx.ipn.escom.pizarron.crud.domain.service.SchoolCycleService;
import mx.ipn.escom.pizarron.crud.port.repository.SchoolCycleRepository;
import mx.ipn.escom.pizarron.crud.util.CreatePageable;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Objects;

import static mx.ipn.escom.pizarron.crud.util.Constants.*;
import static mx.ipn.escom.pizarron.crud.util.Constants.THIS_SCHOOL_CYCLE;

@Service
@Log4j2
public class SchoolCycleServiceImpl implements SchoolCycleService {

    @Autowired
    SchoolCycleRepository schoolCycleRepository;

    @Override
    public SchoolCycleEntity createSchoolCycle(SchoolCycleRequestDto schoolCycleRequestDto) throws BusinessException {
        validateSchoolCycleRequestDtoOnCreate(schoolCycleRequestDto);

        if(schoolCycleRequestDto.getName().isEmpty()) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_SCHOOL_CYCLE);

        validateSchoolCycleNotExist(schoolCycleRequestDto);

        return schoolCycleRepository.save(SchoolCycleEntity.builder().name(schoolCycleRequestDto.getName()).build());
    }

    @Override
    public SchoolCycleEntity updateSchoolCycle(Integer key, SchoolCycleRequestDto schoolCycleRequestDto) throws BusinessException {
        validateSchoolCycleRequestDtoOnUpdate(key, schoolCycleRequestDto);

        SchoolCycleEntity schoolCycleEntity = validateSchoolCycleExist(key);

        if(schoolCycleRequestDto.getName().isEmpty()) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_SCHOOL_CYCLE);

        validateSchoolCycleNotExist(schoolCycleRequestDto);

        schoolCycleEntity.setName(schoolCycleRequestDto.getName());

        return schoolCycleRepository.save(schoolCycleEntity);
    }

    @Override
    public Page<SchoolCycleEntity> getSchoolCycles(DataRequest<SchoolCycleRequestDto> schoolCycleRequestDto) throws BusinessException {
        if(Objects.isNull(schoolCycleRequestDto)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_SCHOOL_CYCLE);
        CreatePageable pageable = new CreatePageable(schoolCycleRequestDto.getPage(), schoolCycleRequestDto.getSize(),
                schoolCycleRequestDto.getSort(), schoolCycleRequestDto.getOrder());
        return schoolCycleRepository.findAllByFilter(schoolCycleRequestDto.getData().getName(), pageable.getPageable());
    }

    @Override
    public SchoolCycleEntity getSchoolCycleByKey(Integer key) throws BusinessException {
        if(Objects.isNull(key)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_SCHOOL_CYCLE);
        return validateSchoolCycleExist(key);
    }

    private SchoolCycleEntity validateSchoolCycleExist(Integer key) throws BusinessException {
        SchoolCycleEntity schoolCycleEntity = schoolCycleRepository.findByIdSchoolCycle(key);
        if(Objects.isNull(schoolCycleEntity)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_SCHOOL_CYCLE);
        return schoolCycleEntity;
    }

    private void validateSchoolCycleNotExist(SchoolCycleRequestDto schoolCycleRequestDto) throws BusinessException {
        if(Objects.nonNull(schoolCycleRepository.findByName(schoolCycleRequestDto.getName())))
            throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + THIS_SCHOOL_CYCLE);
    }

    private void validateSchoolCycleRequestDtoOnCreate(SchoolCycleRequestDto schoolCycleRequestDto) throws BusinessException {
        if(Objects.isNull(schoolCycleRequestDto)
                || Objects.isNull(schoolCycleRequestDto.getName())) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_SCHOOL_CYCLE);
    }

    private void validateSchoolCycleRequestDtoOnUpdate(Integer key, SchoolCycleRequestDto schoolCycleRequestDto) throws BusinessException {
        if(Objects.isNull(schoolCycleRequestDto)
                || key < 0
                || Objects.isNull(schoolCycleRequestDto.getName())) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_SCHOOL_CYCLE);
    }
}
