package mx.ipn.escom.pizarron.crud.port.repository;

import mx.ipn.escom.pizarron.crud.adapter.entity.GroupEntity;
import mx.ipn.escom.pizarron.crud.adapter.entity.GroupSubjectEntity;
import mx.ipn.escom.pizarron.crud.adapter.entity.SchoolCycleEntity;
import mx.ipn.escom.pizarron.crud.adapter.entity.SubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupSubjectRepository extends JpaRepository<GroupSubjectEntity, String> {

    GroupSubjectEntity findByIdGroupSubject(String idGroupSubject);
    GroupSubjectEntity findByIdGroupAndIdSubjectAndIdSchoolCycle(GroupEntity idGroup,
                                                                 SubjectEntity idSubject, SchoolCycleEntity idSchoolCycle);
    Page<GroupSubjectEntity> findAllByIdSchoolCycleAndEnable(SchoolCycleEntity idSchoolCycle, Boolean enable, Pageable pageable);

    @Query(value = "SELECT g FROM GroupSubjectEntity g " +
            "WHERE " +
            "(g.enable = :enable OR :enable IS NULL) " +
            "AND (g.idGroup.name LIKE CONCAT('%',:groupName,'%') OR :groupName IS NULL) " +
            "AND (g.idSubject.name LIKE CONCAT('%',:subjectName,'%') OR :subjectName IS NULL) " +
            "AND (g.idSchoolCycle = :idSchoolCycle OR :idSchoolCycle IS NULL) ")
    Page<GroupSubjectEntity> findAllByFilter(@Param("groupName") String groupName,
                                             @Param("subjectName") String subjectName,
                                             @Param("idSchoolCycle") SchoolCycleEntity idSchoolCycle,
                                             @Param("enable")Boolean enable,
                                             Pageable pageable);
}
