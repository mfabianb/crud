package mx.ipn.escom.pizarron.crud.port.repository;

import mx.ipn.escom.pizarron.crud.adapter.entity.SchoolCycleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolCycleRepository extends JpaRepository<SchoolCycleEntity, Integer> {
    SchoolCycleEntity findByName(String name);
    SchoolCycleEntity findByIdSchoolCycle(Integer idSchoolCycle);
    Page<SchoolCycleEntity> findAll(Pageable pageable);
}
