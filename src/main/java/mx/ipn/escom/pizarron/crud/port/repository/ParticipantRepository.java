package mx.ipn.escom.pizarron.crud.port.repository;

import mx.ipn.escom.pizarron.crud.adapter.entity.ParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository <ParticipantEntity, String>{
}
