package mx.ipn.escom.pizarron.crud.adapter.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.ipn.escom.pizarron.crud.adapter.entity.UserEntity;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private UserEntity user;
    private List<PermissionResponseDto> permissions;
}
