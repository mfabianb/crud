package mx.ipn.escom.pizarron.crud.domain.impl;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.ClassRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.ParticipantRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.DataRequest;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.ClassResponseDto;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.UserResponseDto;
import mx.ipn.escom.pizarron.crud.adapter.entity.ClassEntity;
import mx.ipn.escom.pizarron.crud.adapter.entity.ParticipantEntity;
import mx.ipn.escom.pizarron.crud.adapter.entity.UserEntity;
import mx.ipn.escom.pizarron.crud.domain.service.ClassService;
import mx.ipn.escom.pizarron.crud.domain.service.ParticipantService;
import mx.ipn.escom.pizarron.crud.domain.service.UserService;
import mx.ipn.escom.pizarron.crud.port.repository.ParticipantRepository;
import mx.ipn.escom.pizarron.crud.util.CreatePageable;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static mx.ipn.escom.pizarron.crud.util.Constants.*;

@Service
@Log4j2
public class ParticipantServiceImpl implements ParticipantService {

    @Autowired
    ClassService classService;

    @Autowired
    UserService userService;

    @Autowired
    ParticipantRepository participantRepository;

    @Override
    public ParticipantEntity enrollProfessor(ParticipantRequestDto participantRequestDto) throws BusinessException {
        validateParticipantDtoOnCreate(participantRequestDto);

        ClassEntity classEntity = classService.getClass(participantRequestDto.getIdClass());

        UserEntity userEntity = validateUserIsProfessor(participantRequestDto.getIdUser());

        validateAlreadyEnrolled(classEntity, userEntity);

        finishUpdateClass(classEntity, userEntity);

        return participantRepository.save(ParticipantEntity.builder()
                .idUser(userEntity)
                .idClass(classEntity)
                .enrolled(Boolean.TRUE)
                .pending(Boolean.FALSE)
                .owner(Boolean.TRUE)
                .enrollmentDate(LocalDateTime.now())
                .build());
    }

    @Override
    public ParticipantEntity requestEnrollStudent(ParticipantRequestDto participantRequestDto) throws BusinessException {
        validateParticipantDtoOnCreate(participantRequestDto);

        ClassEntity classEntity = classService.getClass(participantRequestDto.getIdClass());

        UserEntity userEntity = validateUserIsStudent(participantRequestDto.getIdUser());

        validateAlreadyEnrolled(classEntity, userEntity);

        return participantRepository.save(ParticipantEntity.builder()
                .idUser(userEntity)
                .idClass(classEntity)
                .enrolled(Boolean.FALSE)
                .pending(Boolean.TRUE)
                .owner(Boolean.FALSE)
                .enrollmentDate(LocalDateTime.now())
                .build());
    }

    @Override
    public ParticipantEntity attendRequestEnrollmentStudent(String key, ParticipantRequestDto participantRequestDto) throws BusinessException {

        validateParticipantDtoOnCreate(participantRequestDto);

        if(Objects.isNull(key)) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_PARTICIPANT);

        ParticipantEntity participantEntity = validateEnrollmentRequest(key);

        participantEntity.setEnrolled(participantRequestDto.getEnrolled());
        participantEntity.setPending(participantRequestDto.getPending());
        if(Boolean.TRUE.equals(participantRequestDto.getEnrolled())) participantEntity.setEnrollmentDate(LocalDateTime.now());

        return participantRepository.save(participantEntity);
    }

    @Override
    public List<ParticipantEntity> attendAllRequestEnrollmentStudent(ParticipantRequestDto participantRequestDto) throws BusinessException {

        validateParticipantDtoOnCreate(participantRequestDto);

        ClassEntity classEntity = classService.getClass(participantRequestDto.getIdClass());

        List<ParticipantEntity> participantEntityList = participantRepository.findAllByIdClass(classEntity);

        participantEntityList.forEach(participantEntity -> {
            participantEntity.setEnrolled(participantRequestDto.getEnrolled());
            participantEntity.setPending(participantRequestDto.getPending());
            if(Boolean.TRUE.equals(participantRequestDto.getEnrolled())) participantEntity.setEnrollmentDate(LocalDateTime.now());
        });

        return participantRepository.saveAll(participantEntityList);
    }

    @Override
    public Page<ParticipantEntity> getParticipants(DataRequest<ParticipantRequestDto> participantRequestDto){

        CreatePageable pageable = new CreatePageable(participantRequestDto.getPage(), participantRequestDto.getSize(),
                participantRequestDto.getSort(), participantRequestDto.getOrder());

        return participantRepository.findAllByFilter(participantRequestDto.getData().getLastOnline(),
                participantRequestDto.getData().getClassGroup(), participantRequestDto.getData().getClassSubject(),
                participantRequestDto.getData().getClassSchoolCycle(), participantRequestDto.getData().getUserName(),
                participantRequestDto.getData().getUserLastName(), participantRequestDto.getData().getUserSecondLastName(),
                participantRequestDto.getData().getEnrolled(), participantRequestDto.getData().getPending(),
                participantRequestDto.getData().getOwner(), participantRequestDto.getData().getClassEnable(), pageable.getPageable());
    }

    @Override
    public List<ClassResponseDto> getUserClasses(String key) throws BusinessException {
        UserEntity userEntity = userService.getUser(key).getUser();

        List<ClassEntity> classEntityList = participantRepository.findAllClassesByIdUser(userEntity);
        List<ClassResponseDto> classResponseDtoList = new ArrayList<>();

        if(userEntity.getIdUserRol().getIdUserRol() == 3){
            classEntityList.forEach(classEntity ->{
                ClassResponseDto classResponseDto = participantRepository.findAllByIClassAndOwner(classEntity, Boolean.TRUE).get(0);
                classResponseDto.setParticipants(participantRepository.countAllByIdClassAndOwner(classEntity, Boolean.FALSE));
                classResponseDtoList.add(classResponseDto);
            });
        }

        if(userEntity.getIdUserRol().getIdUserRol() == 2){
            classEntityList.forEach(classEntity ->
                classResponseDtoList.addAll(participantRepository.findAllByIClassAndOwner(classEntity, Boolean.FALSE))
            );
        }

        return classResponseDtoList;
    }

    private void validateParticipantDtoOnCreate(ParticipantRequestDto participantRequestDto) throws BusinessException {
        if(Objects.isNull(participantRequestDto)
                || Objects.isNull(participantRequestDto.getIdClass()) || Objects.isNull(participantRequestDto.getIdUser())
                || Objects.isNull(participantRequestDto.getEnrolled()) || Objects.isNull(participantRequestDto.getPending())){
            throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_PARTICIPANT);
        }
    }

    private UserEntity validateUserIsProfessor(String key) throws BusinessException {
        UserResponseDto user = userService.getUser(key);
        if(user.getUser().getIdUserRol().getIdUserRol() == 2){
            return user.getUser();
        }
        throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_PARTICIPANT);
    }

    private UserEntity validateUserIsStudent(String key) throws BusinessException {
        UserResponseDto user = userService.getUser(key);
        if(user.getUser().getIdUserRol().getIdUserRol() == 1){
            return user.getUser();
        }
        throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_PARTICIPANT);
    }

    private void validateAlreadyEnrolled(ClassEntity classEntity, UserEntity userEntity) throws BusinessException {
        if(Objects.nonNull(participantRepository.findByIdClassAndIdUser(classEntity, userEntity)))
            throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + THIS_PARTICIPANT + " EN " + THIS_CLASS);
    }

    private ParticipantEntity validateEnrollmentRequest(String key) throws BusinessException {
        ParticipantEntity participantEntity = participantRepository.findByIdParticipant(key);
        if(Objects.isNull(participantEntity))
            throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + THIS_PARTICIPANT + " EN " + THIS_CLASS);
        return participantEntity;
    }

    private void finishUpdateClass(ClassEntity classEntity, UserEntity userEntity) throws BusinessException {
        ClassRequestDto classRequestDto = ClassRequestDto.builder()
                .key(classEntity.getIdClass())
                .name(classService.getFullName(classEntity))
                .description(classService.getFullName(classEntity) + " IMPARTIDA POR " + userService.getFullName(userEntity))
                .enable(Boolean.TRUE)
                .idGroupSubject(classEntity.getIdGroupSubject().getIdGroupSubject())
                .enrollLink(classEntity.getEnrollLink())
                .creationDate(classEntity.getCreationDate())
                .build();
        classService.updateClass(classEntity.getIdClass(), classRequestDto);
    }

}
