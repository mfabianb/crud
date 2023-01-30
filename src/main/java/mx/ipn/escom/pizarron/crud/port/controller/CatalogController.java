package mx.ipn.escom.pizarron.crud.port.controller;

import lombok.extern.log4j.Log4j2;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.*;
import mx.ipn.escom.pizarron.crud.adapter.dto.request.simple.SimpleRequest;
import mx.ipn.escom.pizarron.crud.adapter.dto.response.simple.DataResponse;
import mx.ipn.escom.pizarron.crud.adapter.entity.*;
import mx.ipn.escom.pizarron.crud.domain.service.CatalogService;
import mx.ipn.escom.pizarron.crud.util.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalog")
@Log4j2
public class CatalogController {

    @Autowired
    CatalogService catalogService;

    @PostMapping("/group")
    public ResponseEntity<DataResponse<GroupEntity>> createGroup(@RequestBody GroupRequestDto groupRequestDto) {
        GroupEntity groupEntity = null;
        try{
            groupEntity = catalogService.createGroup(groupRequestDto);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(groupEntity), HttpStatus.OK);
    }

    @PutMapping("/group")
    public ResponseEntity<DataResponse<GroupEntity>> updateGroup(@RequestBody GroupRequestDto groupRequestDto) {
        GroupEntity groupEntity = null;
        try{
            groupEntity = catalogService.updateGroup(groupRequestDto);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(groupEntity), HttpStatus.OK);
    }

    @GetMapping("/group/{key}")
    public ResponseEntity<DataResponse<GroupEntity>> getGroup(@PathVariable(value="key") String key) {
        GroupEntity groupEntity = null;
        try{
            groupEntity = catalogService.getGroupByKey(key);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(groupEntity), HttpStatus.OK);
    }

    @GetMapping("/group")
    public ResponseEntity<DataResponse<GroupEntity>> getGroup(@RequestBody GroupRequestDto groupRequestDto) {
        GroupEntity groupEntity = null;
        try{
            groupEntity = catalogService.getGroupByName(groupRequestDto);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(groupEntity), HttpStatus.OK);
    }

    @GetMapping("/groups")
    public ResponseEntity<DataResponse<Page<GroupEntity>>> getListGroup(@RequestBody SimpleRequest groupRequestDto) {
        Page<GroupEntity> groupEntity = null;
        try{
            groupEntity = catalogService.getGroups(groupRequestDto);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(groupEntity), HttpStatus.OK);
    }

    @PostMapping("/subject")
    public ResponseEntity<DataResponse<SubjectEntity>> createSubject(@RequestBody SubjectRequestDto subjectRequestDto) {
        SubjectEntity subjectEntity = null;
        try{
            subjectEntity = catalogService.createSubject(subjectRequestDto);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(subjectEntity), HttpStatus.OK);
    }

    @PutMapping("/subject")
    public ResponseEntity<DataResponse<SubjectEntity>> updateSubject(@RequestBody SubjectRequestDto subjectRequestDto) {
        SubjectEntity subjectEntity = null;
        try{
            subjectEntity = catalogService.updateSubject(subjectRequestDto);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(subjectEntity), HttpStatus.OK);
    }

    @GetMapping("/subject/{key}")
    public ResponseEntity<DataResponse<SubjectEntity>> getSubject(@PathVariable(value="key") String key) {
        SubjectEntity subjectEntity = null;
        try{
            subjectEntity = catalogService.getSubjectByKey(key);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(subjectEntity), HttpStatus.OK);
    }

    @GetMapping("/subject")
    public ResponseEntity<DataResponse<SubjectEntity>> getSubject(@RequestBody SubjectRequestDto subjectRequestDto) {
        SubjectEntity subjectEntity = null;
        try{
            subjectEntity = catalogService.getSubjectByName(subjectRequestDto);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(subjectEntity), HttpStatus.OK);
    }

    @GetMapping("/subjects")
    public ResponseEntity<DataResponse<Page<SubjectEntity>>> getListSubject(@RequestBody SimpleRequest subjectRequestDto) {
        Page<SubjectEntity> subjectEntity = null;
        try{
            subjectEntity = catalogService.getSubjects(subjectRequestDto);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(subjectEntity), HttpStatus.OK);
    }

    @GetMapping("/school-cycle")
    public ResponseEntity<DataResponse<SchoolCycleEntity>> getSchoolCycle(@RequestBody SchoolCycleRequestDto schoolCycleRequestDto) {
        SchoolCycleEntity schoolCycleEntity = null;
        try{
            schoolCycleEntity = catalogService.getSchoolCycleByName(schoolCycleRequestDto);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(schoolCycleEntity), HttpStatus.OK);
    }

    @GetMapping("/school-cycles")
    public ResponseEntity<DataResponse<Page<SchoolCycleEntity>>> getListSchoolCycle(@RequestBody SimpleRequest schoolCycleRequestDto) {
        Page<SchoolCycleEntity> schoolCycleEntity = null;
        try{
            schoolCycleEntity = catalogService.getSchoolCycles(schoolCycleRequestDto);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new DataResponse<>(schoolCycleEntity), HttpStatus.OK);
    }

    @PostMapping("/school-cycle")
    public ResponseEntity<DataResponse<SchoolCycleEntity>> createSchoolCycle(@RequestBody SchoolCycleRequestDto schoolCycleRequestDto) {
        SchoolCycleEntity schoolCycleEntity = null;
        try{
            schoolCycleEntity = catalogService.createSchoolCycle(schoolCycleRequestDto);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(schoolCycleEntity), HttpStatus.OK);
    }

    @PutMapping("/school-cycle")
    public ResponseEntity<DataResponse<SchoolCycleEntity>> updateSchoolCycle(@RequestBody SchoolCycleRequestDto schoolCycleRequestDto) {
        SchoolCycleEntity schoolCycleEntity = null;
        try{
            schoolCycleEntity = catalogService.updateSchoolCycle(schoolCycleRequestDto);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(schoolCycleEntity), HttpStatus.OK);
    }

    /**/

    @PostMapping("/group-subject")
    public ResponseEntity<DataResponse<GroupSubjectEntity>> createGroupSubject(@RequestBody GroupSubjectRequestDto groupSubjectRequestDto) {
        GroupSubjectEntity groupSubjectEntity = null;
        try{
            groupSubjectEntity = catalogService.createGroupSubject(groupSubjectRequestDto);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(groupSubjectEntity), HttpStatus.OK);
    }

    @PutMapping("/group-subject")
    public ResponseEntity<DataResponse<GroupSubjectEntity>> updateGroupSubject(@RequestBody GroupSubjectRequestDto groupSubjectRequestDto) {
        GroupSubjectEntity groupSubjectEntity = null;
        try{
            groupSubjectEntity = catalogService.updateGroupSubject(groupSubjectRequestDto);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(groupSubjectEntity), HttpStatus.OK);
    }

    @GetMapping("/group-subject/{key}")
    public ResponseEntity<DataResponse<GroupSubjectEntity>> getGroupSubject(@PathVariable(value="key") String key) {
        GroupSubjectEntity groupSubjectEntity = null;
        try{
            groupSubjectEntity = catalogService.getGroupSubjectByKey(key);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(groupSubjectEntity), HttpStatus.OK);
    }

    @GetMapping("/group-subject-list")
    public ResponseEntity<DataResponse<Page<GroupSubjectEntity>>> getGroupSubjectList(@RequestBody SimpleRequest groupSubjectRequestDto) {
        Page<GroupSubjectEntity> groupSubjectEntity = null;
        try{
            groupSubjectEntity = catalogService.getGroupSubjectsList(groupSubjectRequestDto);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(groupSubjectEntity), HttpStatus.OK);
    }

    /**/

    @GetMapping("/user-status")
    public ResponseEntity<DataResponse<Page<UserStatusEntity>>> getUserStatusList(@RequestBody SimpleRequest simpleRequest) {
        Page<UserStatusEntity> userStatusEntities = null;
        try{
            userStatusEntities = catalogService.getUserStatusList(simpleRequest);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(userStatusEntities), HttpStatus.OK);
    }

    @GetMapping("/user-roles")
    public ResponseEntity<DataResponse<Page<UserRolEntity>>> getUserRolesList(@RequestBody SimpleRequest simpleRequest) {
        Page<UserRolEntity> userStatusEntities = null;
        try{
            userStatusEntities = catalogService.getUserRolList(simpleRequest);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(userStatusEntities), HttpStatus.OK);
    }

    @GetMapping("/permission")
    public ResponseEntity<DataResponse<Page<PermissionEntity>>> getPermissionList(@RequestBody SimpleRequest simpleRequest) {
        Page<PermissionEntity> userStatusEntities = null;
        try{
            userStatusEntities = catalogService.getPermissionList(simpleRequest);
        }catch (Exception businessException){
            return new ResponseEntity<>(new DataResponse<>(null, false, HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessage.setMessage(businessException.getMessage())), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse<>(userStatusEntities), HttpStatus.OK);
    }

}
