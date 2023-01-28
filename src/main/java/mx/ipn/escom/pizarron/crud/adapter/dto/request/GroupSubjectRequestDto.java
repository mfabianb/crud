package mx.ipn.escom.pizarron.crud.adapter.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GroupSubjectRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idGroupSubject;
    private String idGroup;
    private String idSubject;
    private Integer idSchoolCycle;
    private Boolean enable;
}
