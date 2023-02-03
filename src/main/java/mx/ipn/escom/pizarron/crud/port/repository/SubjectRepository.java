package mx.ipn.escom.pizarron.crud.port.repository;

import mx.ipn.escom.pizarron.crud.adapter.entity.SubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {

    SubjectEntity findByName(String name);
    SubjectEntity findByIdSubject(String idSubject);

    @Query(value = "SELECT s FROM SubjectEntity s " +
            "WHERE " +
            "(s.enable = :enable OR :enable IS NULL) " +
            "AND (s.name LIKE CONCAT('%',:name,'%') OR :name IS NULL) ")
    Page<SubjectEntity> findAllByFilter(@Param("name") String name,
                                        @Param("enable") Boolean enable,
                                        Pageable pageable);

}
