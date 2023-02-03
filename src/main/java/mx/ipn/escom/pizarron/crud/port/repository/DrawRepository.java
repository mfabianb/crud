package mx.ipn.escom.pizarron.crud.port.repository;

import mx.ipn.escom.pizarron.crud.adapter.entity.DrawEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrawRepository extends JpaRepository<DrawEntity, String> {
}
