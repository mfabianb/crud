package mx.ipn.escom.pizarron.crud.port.repository;

import mx.ipn.escom.pizarron.crud.adapter.entity.UserEntity;
import mx.ipn.escom.pizarron.crud.adapter.entity.UserRolEntity;
import mx.ipn.escom.pizarron.crud.adapter.entity.UserStatusEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByNameAndLastNameAndSecondLastName(String name, String lastName, String secondLastName);
    UserEntity findByUsername(String username);
    UserEntity findByUsernameAndPassword(String username, String password);
    UserEntity findByEmail(String email);
    UserEntity findByIdUser(String idUser);

    @Query(value = "SELECT u FROM UserEntity u " +
            "WHERE " +
            "(u.username = :username OR :username IS NULL) " +
            "AND (u.email LIKE CONCAT('%',:email,'%') OR :email IS NULL) " +
            "AND (u.name LIKE CONCAT('%',:name,'%') OR :name IS NULL) " +
            "AND (u.lastName LIKE CONCAT('%',:lastName,'%') OR :lastName IS NULL) " +
            "AND (u.secondLastName LIKE CONCAT('%',:secondLastName,'%') OR :secondLastName IS NULL) " +
            "AND (date(u.lastLogin) = date(:lastLogin) OR date(:lastLogin) IS NULL) " +
            "AND (u.idUserStatus = :idUserStatus OR :idUserStatus IS NULL) " +
            "AND (u.idUserRol = :idUserRol OR :idUserRol IS NULL) ")
    Page<UserEntity> findAllByFilter(@Param("username") String username,
                                     @Param("email") String email,
                                     @Param("name") String name,
                                     @Param("lastName") String lastName,
                                     @Param("secondLastName") String secondLastName,
                                     @Param("lastLogin") LocalDateTime lastLogin,
                                     @Param("idUserStatus") UserStatusEntity idUserStatus,
                                     @Param("idUserRol") UserRolEntity idUserRol,
                                     Pageable pageable);
}
