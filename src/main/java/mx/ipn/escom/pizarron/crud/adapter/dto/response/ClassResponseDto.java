package mx.ipn.escom.pizarron.crud.adapter.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.ipn.escom.pizarron.crud.adapter.entity.ClassEntity;
import mx.ipn.escom.pizarron.crud.adapter.entity.UserEntity;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClassResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private ClassEntity idClass;
    private UserEntity idProfessor;
    private Long participants;

}
