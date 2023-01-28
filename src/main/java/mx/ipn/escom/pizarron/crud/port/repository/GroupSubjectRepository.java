package mx.ipn.escom.pizarron.crud.port.repository;

import mx.ipn.escom.pizarron.crud.adapter.entity.GroupEntity;
import mx.ipn.escom.pizarron.crud.adapter.entity.GroupSubjectEntity;
import mx.ipn.escom.pizarron.crud.adapter.entity.SchoolCycleEntity;
import mx.ipn.escom.pizarron.crud.adapter.entity.SubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupSubjectRepository extends JpaRepository<GroupSubjectEntity, String> {

    GroupSubjectEntity findByIdGroupSubject(String idGroupSubject);
    GroupSubjectEntity findByIdGroupAndIdSubjectAndIdSchoolCycle(GroupEntity idGroup,
                                                                 SubjectEntity idSubject, SchoolCycleEntity idSchoolCycle);
    Page<GroupSubjectEntity> findAllByIdSchoolCycleAndEnable(SchoolCycleEntity idSchoolCycle, Boolean enable, Pageable pageable);
}
