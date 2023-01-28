package mx.ipn.escom.pizarron.crud.port.repository;

import mx.ipn.escom.pizarron.crud.adapter.entity.UserRolEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRolRepository extends JpaRepository<UserRolEntity, Integer> {
    UserRolEntity findByRol(String rol);
    UserRolEntity findByIdUserRol(Integer idUserRol);
    Page<UserRolEntity> findAll(Pageable pageable);
}
