package mx.ipn.escom.pizarron.crud.adapter.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idUser;
    private String username;
    private String password;
    private String email;
    private String name;
    private String lastName;
    private String secondLastName;
    private LocalDateTime lastLogin;
    private Integer idUserStatus;
    private Integer idUserRol;
}
