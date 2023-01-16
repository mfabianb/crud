package mx.ipn.escom.pizarron.crud.port.repository;

import mx.ipn.escom.pizarron.crud.adapter.entity.GroupSubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupSubjectRepository extends JpaRepository<GroupSubjectEntity, String> {
}
