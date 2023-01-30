package mx.ipn.escom.pizarron.crud.domain.impl;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.*;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.DataRequest;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.SimpleRequest;
import mx.ipn.escom.pizarron.crud.adapter.entity.*;
import mx.ipn.escom.pizarron.crud.domain.service.CatalogService;
import mx.ipn.escom.pizarron.crud.port.repository.*;
import mx.ipn.escom.pizarron.crud.util.CreatePageable;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static mx.ipn.escom.pizarron.crud.util.Constants.*;

@Service
@Log4j2
public class CatalogServiceImpl implements CatalogService {

    @Value("${crud.current-cycle}")
    String currentSchoolCycle;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    SchoolCycleRepository schoolCycleRepository;

    @Autowired
    GroupSubjectRepository groupSubjectRepository;

    @Autowired
    UserStatusRepository userStatusRepository;

    @Autowired
    UserRolRepository userRolRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void init(){
        log.debug("Init service {}", this.getClass().getName());
    }

    /*
     * Grupos
     */

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
    public GroupEntity updateGroup(GroupRequestDto groupRequestDto) throws BusinessException {
        validateGroupRequestDtoOnUpdate(groupRequestDto);

        GroupEntity groupEntity = validateGroupExist(groupRequestDto.getKey());

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

    private void validateGroupRequestDtoOnUpdate(GroupRequestDto groupRequestDto) throws BusinessException {
        if(Objects.isNull(groupRequestDto)
                || Objects.isNull(groupRequestDto.getKey())
                || Objects.isNull(groupRequestDto.getName())
                || Objects.isNull(groupRequestDto.getEnable())) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_GROUP);
    }

    /*
     * Materia
     */

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
    public SubjectEntity updateSubject(SubjectRequestDto subjectRequestDto) throws BusinessException {
        validateSubjectRequestDtoOnUpdate(subjectRequestDto);

        SubjectEntity subjectEntity = validateSubjectExist(subjectRequestDto.getKey());

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
    public Page<SubjectEntity> getSubjects(SimpleRequest subjectRequestDto) throws BusinessException {
        if(Objects.isNull(subjectRequestDto)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_SUBJECT);
        CreatePageable pageable = new CreatePageable(subjectRequestDto.getPage(), subjectRequestDto.getSize(),
                subjectRequestDto.getSort(), subjectRequestDto.getOrder());
        return subjectRepository.findAllByEnable(Boolean.TRUE, pageable.getPageable());
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

    private void validateSubjectRequestDtoOnUpdate(SubjectRequestDto subjectRequestDto) throws BusinessException {
        if(Objects.isNull(subjectRequestDto)
                || Objects.isNull(subjectRequestDto.getKey())
                || Objects.isNull(subjectRequestDto.getName())
                || Objects.isNull(subjectRequestDto.getEnable())) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_SUBJECT);
    }

    /*
     * Ciclo escolar
     */

    @Override
    public SchoolCycleEntity createSchoolCycle(SchoolCycleRequestDto schoolCycleRequestDto) throws BusinessException {
        validateSchoolCycleRequestDtoOnCreate(schoolCycleRequestDto);

        if(schoolCycleRequestDto.getName().isEmpty()) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_SCHOOL_CYCLE);

        validateSchoolCycleNotExist(schoolCycleRequestDto);

        return schoolCycleRepository.save(SchoolCycleEntity.builder().name(schoolCycleRequestDto.getName()).build());
    }

    @Override
    public SchoolCycleEntity updateSchoolCycle(SchoolCycleRequestDto schoolCycleRequestDto) throws BusinessException {
        validateSchoolCycleRequestDtoOnUpdate(schoolCycleRequestDto);

        SchoolCycleEntity schoolCycleEntity = validateSchoolCycleExist(schoolCycleRequestDto.getKey());

        if(schoolCycleRequestDto.getName().isEmpty()) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_SCHOOL_CYCLE);

        validateSchoolCycleNotExist(schoolCycleRequestDto);

        schoolCycleEntity.setName(schoolCycleRequestDto.getName());

        return schoolCycleRepository.save(schoolCycleEntity);
    }

    @Override
    public SchoolCycleEntity getSchoolCycleByName(SchoolCycleRequestDto schoolCycleRequestDto) throws BusinessException {
        if(Objects.isNull(schoolCycleRequestDto)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_SCHOOL_CYCLE);
        return validateSchoolCycleExist(schoolCycleRequestDto);
    }

    @Override
    public Page<SchoolCycleEntity> getSchoolCycles(SimpleRequest schoolCycleRequestDto) throws BusinessException {
        if(Objects.isNull(schoolCycleRequestDto)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_SCHOOL_CYCLE);
        CreatePageable pageable = new CreatePageable(schoolCycleRequestDto.getPage(), schoolCycleRequestDto.getSize(),
                schoolCycleRequestDto.getSort(), schoolCycleRequestDto.getOrder());
        return schoolCycleRepository.findAll(pageable.getPageable());
    }

    private SchoolCycleEntity getSchoolCycleByKey(Integer key) throws BusinessException {
        if(Objects.isNull(key)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_SCHOOL_CYCLE);
        return validateSchoolCycleExist(key);
    }

    private SchoolCycleEntity validateSchoolCycleExist(SchoolCycleRequestDto schoolCycleRequestDto) throws BusinessException {
        SchoolCycleEntity schoolCycleEntity = schoolCycleRepository.findByName(schoolCycleRequestDto.getName());
        if(Objects.isNull(schoolCycleEntity)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_SCHOOL_CYCLE);
        return schoolCycleEntity;
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

    private void validateSchoolCycleRequestDtoOnUpdate(SchoolCycleRequestDto schoolCycleRequestDto) throws BusinessException {
        if(Objects.isNull(schoolCycleRequestDto)
                || schoolCycleRequestDto.getKey() < 0
                || Objects.isNull(schoolCycleRequestDto.getName())) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_SCHOOL_CYCLE);
    }

    /*
     * Grupo-Materia-Ciclo Escolar
     */

    @Override
    public GroupSubjectEntity createGroupSubject(GroupSubjectRequestDto groupSubjectRequestDto) throws BusinessException {

        GroupEntity groupEntity = getGroupByKey(groupSubjectRequestDto.getIdGroup());
        SubjectEntity subjectEntity = getSubjectByKey(groupSubjectRequestDto.getIdSubject());
        SchoolCycleEntity schoolCycleEntity = getSchoolCycleByKey(groupSubjectRequestDto.getIdSchoolCycle());

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
    public GroupSubjectEntity updateGroupSubject(GroupSubjectRequestDto groupSubjectRequestDto) throws BusinessException {

        if(Objects.isNull(groupSubjectRequestDto) || Objects.isNull(groupSubjectRequestDto.getIdGroupSubject()))
            throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_RESOURCE);

        GroupSubjectEntity groupSubjectEntity = groupSubjectRepository.findByIdGroupSubject(groupSubjectRequestDto.getIdGroupSubject());

        GroupEntity groupEntity = getGroupByKey(groupSubjectRequestDto.getIdGroup());
        SubjectEntity subjectEntity = getSubjectByKey(groupSubjectRequestDto.getIdSubject());
        SchoolCycleEntity schoolCycleEntity = getSchoolCycleByKey(groupSubjectRequestDto.getIdSchoolCycle());

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
    public Page<GroupSubjectEntity> getGroupSubjectsList(SimpleRequest simpleRequest) throws BusinessException {

        if(Objects.isNull(simpleRequest)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_RESOURCE);

        CreatePageable pageable = new CreatePageable(simpleRequest.getPage(), simpleRequest.getSize(),
                simpleRequest.getSort(), simpleRequest.getOrder());

        SchoolCycleEntity schoolCycleEntity = SchoolCycleEntity.builder().idSchoolCycle(Integer.parseInt(currentSchoolCycle)).build();

        Page<GroupSubjectEntity> groupSubjectEntityList = groupSubjectRepository.findAllByIdSchoolCycleAndEnable(schoolCycleEntity,
                Boolean.TRUE, pageable.getPageable());

        if(Objects.isNull(groupSubjectEntityList)){
            throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_RESOURCE);
        }

        return groupSubjectEntityList;

    }

    /*
     * Estatus de usuario
     */

    @Override
    public UserStatusEntity createUserStatus(UserStatusRequestDto userStatusRequestDto) throws BusinessException {
        if(Objects.isNull(userStatusRequestDto)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_USER_STATUS);

        if(userStatusRequestDto.getStatus().isEmpty() || userStatusRequestDto.getDescription().isEmpty())
            throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_USER_STATUS);

        validateUserStatusNotExist(userStatusRequestDto);

        return userStatusRepository.save(UserStatusEntity.builder()
                .status(userStatusRequestDto.getStatus())
                .description(userStatusRequestDto.getDescription())
                .build());
    }

    @Override
    public Page<UserStatusEntity> getUserStatusList(SimpleRequest simpleRequest) throws BusinessException {

        if(Objects.isNull(simpleRequest)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_RESOURCE);

        CreatePageable pageable = new CreatePageable(simpleRequest.getPage(), simpleRequest.getSize(),
                simpleRequest.getSort(), simpleRequest.getOrder());

        return userStatusRepository.findAll(pageable.getPageable());

    }

    private void validateUserStatusNotExist(UserStatusRequestDto userStatusRequestDto) throws BusinessException {
        if(Objects.nonNull(userStatusRepository.findByStatus(userStatusRequestDto.getStatus())))
            throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + THIS_USER_STATUS);
    }

    /*
     * Rol de usuario
     */

    @Override
    public UserRolEntity createUserRol(UserRolRequestDto userRolRequestDto) throws BusinessException {
        if(Objects.isNull(userRolRequestDto)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_USER_STATUS);

        if(userRolRequestDto.getRol().isEmpty() || userRolRequestDto.getDescription().isEmpty())
            throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_USER_STATUS);

        validateUserRolNotExist(userRolRequestDto);

        return userRolRepository.save(UserRolEntity.builder()
                .rol(userRolRequestDto.getRol())
                .description(userRolRequestDto.getDescription())
                .build());
    }

    @Override
    public Page<UserRolEntity> getUserRolList(SimpleRequest simpleRequest) throws BusinessException {

        if(Objects.isNull(simpleRequest)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_RESOURCE);

        CreatePageable pageable = new CreatePageable(simpleRequest.getPage(), simpleRequest.getSize(),
                simpleRequest.getSort(), simpleRequest.getOrder());

        return userRolRepository.findAll(pageable.getPageable());

    }

    private void validateUserRolNotExist(UserRolRequestDto userRolRequestDto) throws BusinessException {
        if(Objects.nonNull(userRolRepository.findByRol(userRolRequestDto.getRol())))
            throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + THIS_USER_STATUS);
    }

    /*
     * Permisos
    * */

    @Override
    public PermissionEntity createPermission(PermissionRequestDto permissionRequestDto) throws BusinessException {
        if(Objects.isNull(permissionRequestDto)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_USER_STATUS);

        if(permissionRequestDto.getName().isEmpty() || permissionRequestDto.getDescription().isEmpty())
            throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_USER_STATUS);

        validatePermissionNotExist(permissionRequestDto);

        return permissionRepository.save(PermissionEntity.builder()
                .name(permissionRequestDto.getName())
                .description(permissionRequestDto.getDescription())
                .build());
    }

    @Override
    public Page<PermissionEntity> getPermissionList(SimpleRequest simpleRequest) throws BusinessException {

        if(Objects.isNull(simpleRequest)) throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_RESOURCE);

        CreatePageable pageable = new CreatePageable(simpleRequest.getPage(), simpleRequest.getSize(),
                simpleRequest.getSort(), simpleRequest.getOrder());

        return permissionRepository.findAll(pageable.getPageable());

    }

    private void validatePermissionNotExist(PermissionRequestDto permissionRequestDto) throws BusinessException {
        if(Objects.nonNull(permissionRepository.findByName(permissionRequestDto.getName())))
            throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + THIS_USER_STATUS);
    }

    /*
     * Usuarios
     * */

    @Override
    public UserEntity createUser(UserRequestDto userRequestDto) throws BusinessException{
        validateUserDtoOnCreate(userRequestDto);

        validateUserNotExistOnCreate(userRequestDto);

        return userRepository.save(UserEntity.builder()
                .username(userRequestDto.getUsername())
                .password(userRequestDto.getPassword())
                .email(userRequestDto.getEmail())
                .name(userRequestDto.getName())
                .lastName(userRequestDto.getLastName())
                .secondLastName(userRequestDto.getSecondLastName())
                .idUserStatus(UserStatusEntity.builder().idUserStatus(userRequestDto.getIdUserStatus()).build())
                .idUserRol(UserRolEntity.builder().idUserRol(userRequestDto.getIdUserRol()).build())
                .build());
    }

    @Override
    public UserEntity updateUser(UserRequestDto userRequestDto) throws BusinessException{

        validateUserDtoOnUpdate(userRequestDto);

        UserEntity userEntity = validateUserNotExistOnUpdate(userRequestDto);

        Boolean hasChanges = validateUserDataOnUpdate(userRequestDto, userEntity);

        if (Boolean.TRUE.equals(hasChanges)) return userRepository.save(userEntity);
        else throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + "LA INFORMACIÓN DE " + THIS_USER);
    }

    @Override
    public Page<UserEntity> getUserList(DataRequest<UserRequestDto> userRequestDto) throws BusinessException{
        CreatePageable pageable = new CreatePageable(userRequestDto.getPage(), userRequestDto.getSize(),
                userRequestDto.getSort(), userRequestDto.getOrder());
        return userRepository.findAllByFilter(userRequestDto.getData().getUsername(), userRequestDto.getData().getEmail(),
                userRequestDto.getData().getName(), userRequestDto.getData().getLastName(), userRequestDto.getData().getSecondLastName(),
                userRequestDto.getData().getLastLogin(),
                UserStatusEntity.builder().idUserStatus(userRequestDto.getData().getIdUserStatus()).build(),
                UserRolEntity.builder().idUserRol(userRequestDto.getData().getIdUserRol()).build(), pageable.getPageable());
    }

    @Override
    public UserEntity getUser(UserRequestDto userRequestDto) throws BusinessException {
        if(Objects.isNull(userRequestDto) || Objects.isNull(userRequestDto.getIdUser()))
            throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_USER);
        return validateUserNotExistOnUpdate(userRequestDto);
    }

    private Boolean validateFullName(UserRequestDto userRequestDto, UserEntity userEntity){
        UserEntity userByName = userRepository.findByNameAndLastNameAndSecondLastName(userRequestDto.getName(),
                userRequestDto.getLastName(), userRequestDto.getSecondLastName());

        Boolean nameUpdate = Boolean.FALSE;
        Boolean lastNameUpdate = Boolean.FALSE;
        Boolean secondLastNameUpdate = Boolean.FALSE;

        if(Objects.isNull(userByName)){
            if(!userEntity.getName().toUpperCase(Locale.ROOT).equals(userRequestDto.getName().toUpperCase(Locale.ROOT))){
                userEntity.setName(userRequestDto.getName());
                nameUpdate = Boolean.TRUE;
            }

            if(!userEntity.getLastName().toUpperCase(Locale.ROOT).equals(userRequestDto.getLastName().toUpperCase(Locale.ROOT))) {
                userEntity.setLastName(userRequestDto.getLastName());
                lastNameUpdate = Boolean.TRUE;
            }

            if(!userEntity.getSecondLastName().toUpperCase(Locale.ROOT).equals(userRequestDto.getSecondLastName().toUpperCase(Locale.ROOT))) {
                userEntity.setSecondLastName(userRequestDto.getSecondLastName());
                secondLastNameUpdate = Boolean.TRUE;
            }
        }

        return nameUpdate || lastNameUpdate || secondLastNameUpdate;
    }

    private Boolean validateUserName(UserRequestDto userRequestDto, UserEntity userEntity){
        UserEntity userByUserName = userRepository.findByUsername(userRequestDto.getUsername());

        if(Objects.isNull(userByUserName) &&
                !userEntity.getUsername().toUpperCase(Locale.ROOT).equals(userRequestDto.getUsername().toUpperCase(Locale.ROOT))){
            userEntity.setUsername(userRequestDto.getUsername());
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private Boolean validateEmail(UserRequestDto userRequestDto, UserEntity userEntity){
        UserEntity userByEmail = userRepository.findByEmail(userRequestDto.getEmail());

        if(Objects.isNull(userByEmail) &&
                !userEntity.getEmail().toUpperCase(Locale.ROOT).equals(userRequestDto.getEmail().toUpperCase(Locale.ROOT))){
            userEntity.setEmail(userRequestDto.getEmail());
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private Boolean validateUserDataOnUpdate(UserRequestDto userRequestDto, UserEntity userEntity){

        Boolean validFullName = validateFullName(userRequestDto, userEntity);

        Boolean validUserName = validateUserName(userRequestDto, userEntity);

        Boolean validEmail = validateEmail(userRequestDto, userEntity);

        Boolean validPassword = Boolean.FALSE;

        Boolean validUserStatus = Boolean.FALSE;

        Boolean validUserRol = Boolean.FALSE;

        if(!userEntity.getPassword().toUpperCase(Locale.ROOT).equals(userRequestDto.getPassword().toUpperCase(Locale.ROOT))) {
            userEntity.setPassword(userRequestDto.getPassword());
            validPassword = Boolean.TRUE;
        }

        if(!userEntity.getIdUserStatus().getIdUserStatus().equals(userRequestDto.getIdUserStatus())) {
            userEntity.setIdUserStatus(UserStatusEntity.builder().idUserStatus(userRequestDto.getIdUserStatus()).build());
            validUserStatus = Boolean.TRUE;
        }

        if(!userEntity.getIdUserRol().getIdUserRol().equals(userRequestDto.getIdUserRol())) {
            userEntity.setIdUserRol(UserRolEntity.builder().idUserRol(userRequestDto.getIdUserRol()).build());
            validUserRol = Boolean.TRUE;
        }

        return validFullName || validUserName || validEmail || validPassword || validUserStatus || validUserRol;
    }

    private void validateUserDtoOnCreate(UserRequestDto userRequestDto) throws BusinessException {
        if(Objects.isNull(userRequestDto)
                || Objects.isNull(userRequestDto.getUsername()) || userRequestDto.getUsername().isEmpty()
                || Objects.isNull(userRequestDto.getPassword()) || userRequestDto.getPassword().isEmpty()
                || Objects.isNull(userRequestDto.getEmail()) || userRequestDto.getEmail().isEmpty()
                || Objects.isNull(userRequestDto.getName()) || userRequestDto.getName().isEmpty()
                || Objects.isNull(userRequestDto.getLastName()) || userRequestDto.getLastName().isEmpty()
                || Objects.isNull(userRequestDto.getSecondLastName()) || userRequestDto.getSecondLastName().isEmpty()
                || userRequestDto.getIdUserStatus() <= 0
                || userRequestDto.getIdUserRol() <= 0 ) throw new BusinessException(CREATE_DATA_REQUEST_NULL + THIS_USER);
    }

    private void validateUserDtoOnUpdate(UserRequestDto userRequestDto) throws BusinessException {
        validateUserDtoOnCreate(userRequestDto);
        if(Objects.isNull(userRequestDto.getIdUser()))
            throw new BusinessException(MODIFY_DATA_REQUEST_NULL + THIS_USER);
    }

    private void validateUserNotExistOnCreate(UserRequestDto userRequestDto) throws BusinessException {
        if(Objects.nonNull(userRepository.findByNameAndLastNameAndSecondLastName(userRequestDto.getName(),
                userRequestDto.getLastName(), userRequestDto.getSecondLastName()))
                || Objects.nonNull(userRepository.findByUsername(userRequestDto.getUsername()))
                || Objects.nonNull(userRepository.findByEmail(userRequestDto.getEmail())))
            throw new BusinessException(ALREADY_EXIST_DATA_REQUEST + THIS_USER);
    }

    private UserEntity validateUserNotExistOnUpdate(UserRequestDto userRequestDto) throws BusinessException {
        UserEntity userEntity = userRepository.findByIdUser(userRequestDto.getIdUser());
        if(Objects.isNull(userEntity))
            throw new BusinessException(NOT_FOUND_DATA_REQUEST + THIS_USER);
        return userEntity;
    }

}
