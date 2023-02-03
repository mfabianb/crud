package mx.ipn.escom.pizarron.crud.port.repository;

import mx.ipn.escom.pizarron.crud.adapter.entity.GroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, String> {
    GroupEntity findByName(String name);
    GroupEntity findByIdGroup(String idGroup);

    @Query(value = "SELECT g FROM GroupEntity g " +
            "WHERE " +
            "(g.enable = :enable OR :enable IS NULL) " +
            "AND (g.name LIKE CONCAT('%',:name,'%') OR :name IS NULL) ")
    Page<GroupEntity> findAllByFilter(@Param("name") String name,
                                      @Param("enable") Boolean enable,
                                      Pageable pageable);
}
