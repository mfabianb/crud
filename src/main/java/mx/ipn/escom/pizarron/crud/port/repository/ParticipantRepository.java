package mx.ipn.escom.pizarron.crud.port.repository;

import mx.ipn.escom.pizarron.crud.adapter.entity.ClassEntity;
import mx.ipn.escom.pizarron.crud.adapter.entity.ParticipantEntity;
import mx.ipn.escom.pizarron.crud.adapter.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository <ParticipantEntity, String>{

    ParticipantEntity findByIdClassAndIdUser(ClassEntity idClass, UserEntity idUser);
    ParticipantEntity findByIdParticipant(String idParticipant);
    List<ParticipantEntity> findAllByIdClass(ClassEntity idClass);
    @Query("SELECT new mx.ipn.escom.pizarron.crud.adapter.dto.response.ClassResponseDto(p.idClass, p.idUser, count(p.idClass)) " +
            "FROM ParticipantEntity p " +
            "WHERE " +
            "(p.owner = :owner OR :owner IS NULL)" +
            "AND (p.idClass = :idClass OR :idClass IS NULL)" +
            "AND (p.idUser = :idUser OR :idUser IS NULL) " +
            "GROUP BY p.idClass")
    Page<ParticipantEntity> findAllByIdUser(@Param("idUser") UserEntity idUser,
                                            @Param("idClass") ClassEntity idClass,
                                            @Param("owner") Boolean owner,
                                            Pageable pageable);

    @Query(value = "SELECT p FROM ParticipantEntity p " +
            "WHERE " +
            "(p.enrolled = :enrolled OR :enrolled IS NULL) " +
            "AND (p.pending = :pending OR :pending IS NULL) " +
            "AND (p.owner = :owner OR :owner IS NULL) " +
            "AND (p.lastOnline = :lastOnline OR :lastOnline IS NULL) " +
            "AND (p.enrollmentDate = :enrollmentDate OR :enrollmentDate IS NULL) " +
            "AND (p.idClass.idGroupSubject.idGroup.name = :classGroup OR :classGroup IS NULL) " +
            "AND (p.idClass.idGroupSubject.idSubject.name = :classSubject OR :classSubject IS NULL) " +
            "AND (p.idClass.idGroupSubject.idSchoolCycle.name = :classSchoolCycle OR :classSchoolCycle IS NULL) " +
            "AND (p.idUser.name = :userName OR :userName IS NULL) " +
            "AND (p.idUser.lastName = :userLastName OR :userLastName IS NULL) " +
            "AND (p.idUser.secondLastName = :userSecondLastName OR :userSecondLastName IS NULL) " +
            "AND (p.idClass.enable = :classEnable OR :classEnable IS NULL)")
    Page<ParticipantEntity> findAllByFilter(@Param("lastOnline") LocalDateTime lastOnline,
                                            @Param("classGroup") String classGroup,
                                            @Param("classSubject") String classSubject,
                                            @Param("classSchoolCycle") String classSchoolCycle,
                                            @Param("userName") String userName,
                                            @Param("userLastName") String userLastName,
                                            @Param("userSecondLastName") String userSecondLastName,
                                            @Param("enrolled") Boolean enrolled,
                                            @Param("pending") Boolean pending,
                                            @Param("owner") Boolean owner,
                                            @Param("classEnable") Boolean classEnable,
                                            Pageable pageable);
}
