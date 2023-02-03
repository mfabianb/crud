package mx.ipn.escom.pizarron.crud.domain.service;

import mx.ipn.escom.pizarron.crud.adapter.dto.request.SubjectRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.SubjectsRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.DataRequest;
import mx.ipn.escom.pizarron.crud.adapter.entity.SubjectEntity;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubjectService {
    SubjectEntity createSubject(SubjectRequestDto subjectRequestDto) throws BusinessException;

    List<SubjectEntity> createSubjects(SubjectsRequestDto subjectsRequestDto) throws BusinessException;

    SubjectEntity updateSubject(String key, SubjectRequestDto subjectRequestDto) throws BusinessException;

    SubjectEntity getSubjectByName(SubjectRequestDto subjectRequestDto) throws BusinessException;

    SubjectEntity getSubjectByKey(String key) throws BusinessException;

    Page<SubjectEntity> getSubjects(DataRequest<SubjectRequestDto> subjectRequestDto) throws BusinessException;
}
