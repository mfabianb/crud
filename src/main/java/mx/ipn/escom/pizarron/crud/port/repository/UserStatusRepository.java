package mx.ipn.escom.pizarron.crud.port.repository;

import mx.ipn.escom.pizarron.crud.adapter.entity.UserStatusEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatusEntity, Integer> {
    UserStatusEntity findByStatus(String status);
    UserStatusEntity findByIdUserStatus(Integer idUserStatus);
    Page<UserStatusEntity> findAll(Pageable pageable);
}
