package mx.ipn.escom.pizarron.crud.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@Table(name = "board_permission")
@XmlRootElement
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BoardPermissionEntity implements Serializable {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "id_board_permission")
    private UUID idBoardPermission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_board", referencedColumnName = "id_board")
    private BoardEntity idBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_permission", referencedColumnName = "id_permission")
    private PermissionEntity idPermission;
}
