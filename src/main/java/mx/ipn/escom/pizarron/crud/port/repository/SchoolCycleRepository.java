package mx.ipn.escom.pizarron.crud.port.repository;

import mx.ipn.escom.pizarron.crud.adapter.entity.SchoolCycleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolCycleRepository extends JpaRepository<SchoolCycleEntity, Integer> {
    SchoolCycleEntity findByName(String name);
    SchoolCycleEntity findByIdSchoolCycle(Integer idSchoolCycle);

    @Query(value = "SELECT s FROM SchoolCycleEntity s " +
            "WHERE " +
            "(s.name LIKE CONCAT('%',:name,'%') OR :name IS NULL) ")
    Page<SchoolCycleEntity> findAllByFilter(@Param("name") String name , Pageable pageable);
}
