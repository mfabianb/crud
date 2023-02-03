package mx.ipn.escom.pizarron.crud.domain.service;

import mx.ipn.escom.pizarron.crud.adapter.dto.request.ParticipantRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.DataRequest;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.ClassResponseDto;
import mx.ipn.escom.pizarron.crud.adapter.entity.ParticipantEntity;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ParticipantService {
    ParticipantEntity enrollProfessor(ParticipantRequestDto participantRequestDto) throws BusinessException;

    ParticipantEntity requestEnrollStudent(ParticipantRequestDto participantRequestDto) throws BusinessException;

    ParticipantEntity attendRequestEnrollmentStudent(String key, ParticipantRequestDto participantRequestDto) throws BusinessException;

    List<ParticipantEntity> attendAllRequestEnrollmentStudent(ParticipantRequestDto participantRequestDto) throws BusinessException;

    Page<ParticipantEntity> getParticipants(DataRequest<ParticipantRequestDto> participantRequestDto);

    List<ClassResponseDto> getUserClasses(String key) throws BusinessException;
}
