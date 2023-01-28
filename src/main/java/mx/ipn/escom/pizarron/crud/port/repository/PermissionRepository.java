package mx.ipn.escom.pizarron.crud.port.repository;

import mx.ipn.escom.pizarron.crud.adapter.entity.PermissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository <PermissionEntity, Integer>{
    PermissionEntity findByName(String name);
    PermissionEntity findByIdPermission(Integer idPermission);
    Page<PermissionEntity> findAll(Pageable pageable);
}
