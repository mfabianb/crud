package mx.ipn.escom.pizarron.crud.port.repository;

import mx.ipn.escom.pizarron.crud.adapter.entity.SubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {

    SubjectEntity findByName(String name);
    SubjectEntity findByIdSubject(String idSubject);
    Page<SubjectEntity> findAllByEnable(Boolean enable, Pageable pageable);

}
