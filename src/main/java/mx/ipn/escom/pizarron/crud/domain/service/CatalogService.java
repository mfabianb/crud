package mx.ipn.escom.pizarron.crud.domain.service;

import mx.ipn.escom.pizarron.crud.adapter.dto.request.*;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.SimpleRequest;
import mx.ipn.escom.pizarron.crud.adapter.entity.*;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatalogService {

    GroupEntity createGroup(GroupRequestDto groupRequestDto) throws BusinessException;

    List<GroupEntity> createGroups(GroupsRequestDto groupsRequestDto) throws BusinessException;

    GroupEntity updateGroup(GroupRequestDto groupRequestDto) throws BusinessException;

    GroupEntity getGroupByName(GroupRequestDto groupRequestDto) throws BusinessException;

    GroupEntity getGroupByKey(String key) throws BusinessException;

    Page<GroupEntity> getGroups(SimpleRequest groupRequestDto) throws BusinessException;

    SubjectEntity createSubject(SubjectRequestDto subjectRequestDto) throws BusinessException;

    List<SubjectEntity> createSubjects(SubjectsRequestDto subjectsRequestDto) throws BusinessException;

    SubjectEntity updateSubject(SubjectRequestDto subjectRequestDto) throws BusinessException;

    SubjectEntity getSubjectByName(SubjectRequestDto subjectRequestDto) throws BusinessException;

    SubjectEntity getSubjectByKey(String key) throws BusinessException;

    Page<SubjectEntity> getSubjects(SimpleRequest subjectRequestDto) throws BusinessException;

    SchoolCycleEntity createSchoolCycle(SchoolCycleRequestDto schoolCycleRequestDto) throws BusinessException;

    SchoolCycleEntity updateSchoolCycle(SchoolCycleRequestDto schoolCycleRequestDto) throws BusinessException;

    SchoolCycleEntity getSchoolCycleByName(SchoolCycleRequestDto schoolCycleRequestDto) throws BusinessException;

    Page<SchoolCycleEntity> getSchoolCycles(SimpleRequest schoolCycleRequestDto) throws BusinessException;

    GroupSubjectEntity createGroupSubject(GroupSubjectRequestDto groupSubjectRequestDto) throws BusinessException;

    GroupSubjectEntity updateGroupSubject(GroupSubjectRequestDto groupSubjectRequestDto) throws BusinessException;

    GroupSubjectEntity getGroupSubjectByKey(String key) throws BusinessException;

    Page<GroupSubjectEntity> getGroupSubjectsList(SimpleRequest simpleRequest) throws BusinessException;

    UserStatusEntity createUserStatus(UserStatusRequestDto userStatusRequestDto) throws BusinessException;

    Page<UserStatusEntity> getUserStatusList(SimpleRequest simpleRequest) throws BusinessException;

    UserRolEntity createUserRol(UserRolRequestDto userRolRequestDto) throws BusinessException;

    Page<UserRolEntity> getUserRolList(SimpleRequest simpleRequest) throws BusinessException;

    PermissionEntity createPermission(PermissionRequestDto permissionRequestDto) throws BusinessException;

    Page<PermissionEntity> getPermissionList(SimpleRequest simpleRequest) throws BusinessException;
}
