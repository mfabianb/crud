package mx.ipn.escom.pizarron.crud.domain.service;

import mx.ipn.escom.pizarron.crud.adapter.dto.request.SchoolCycleRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.DataRequest;
import mx.ipn.escom.pizarron.crud.adapter.entity.SchoolCycleEntity;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.springframework.data.domain.Page;

public interface SchoolCycleService {
    SchoolCycleEntity createSchoolCycle(SchoolCycleRequestDto schoolCycleRequestDto) throws BusinessException;

    SchoolCycleEntity updateSchoolCycle(Integer key, SchoolCycleRequestDto schoolCycleRequestDto) throws BusinessException;

    Page<SchoolCycleEntity> getSchoolCycles(DataRequest<SchoolCycleRequestDto> schoolCycleRequestDto) throws BusinessException;

    SchoolCycleEntity getSchoolCycleByKey(Integer key) throws BusinessException;
}
