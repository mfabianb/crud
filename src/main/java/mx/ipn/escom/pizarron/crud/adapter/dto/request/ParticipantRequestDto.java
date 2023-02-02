package mx.ipn.escom.pizarron.crud.adapter.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ParticipantRequestDto {
    private static final long serialVersionUID = 1L;

    private String idParticipant;
    private LocalDateTime enrollmentDate;
    private LocalDateTime lastOnline;

    private String idClass;
    private String classGroup;
    private String classSubject;
    private String classSchoolCycle;
    private Boolean classEnable;

    private String idUser;
    private String userName;
    private String userLastName;
    private String userSecondLastName;

    private Boolean enrolled;
    private Boolean pending;
    private Boolean owner;
}
