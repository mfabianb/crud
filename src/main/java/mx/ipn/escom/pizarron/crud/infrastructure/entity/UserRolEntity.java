package mx.ipn.escom.pizarron.crud.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Data
@Table(name = "user_rol")
@XmlRootElement
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserRolEntity implements Serializable {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "id_user_rol")
    private Integer idUserRol;

    @Basic
    @NotNull
    @Size(max = 20)
    @Column(name = "rol")
    private String rol;

    @Basic
    @NotNull
    @Size(max = 60)
    @Column(name = "description")
    private String description;
}
