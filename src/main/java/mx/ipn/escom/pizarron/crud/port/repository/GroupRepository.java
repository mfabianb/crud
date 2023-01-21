package mx.ipn.escom.pizarron.crud.port.repository;

import mx.ipn.escom.pizarron.crud.adapter.entity.GroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, String> {
    GroupEntity findByName(String name);
    GroupEntity findByIdGroup(String idGroup);
    Page<GroupEntity> findAllByEnable(Boolean enable, Pageable pageable);
}
