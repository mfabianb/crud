package mx.ipn.escom.pizarron.crud.adapter.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@XmlRootElement
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserEntity implements Serializable {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "id_user")
    @Size(max = 36)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String idUser;

    @Basic(optional = false)
    @Size(max = 24)
    @Column(name = "username")
    private String username;

    @Basic(optional = false)
    @Size(max = 255)
    @Column(name = "password")
    private String password;

    @Basic
    @Size(max = 100)
    @Column(name = "email")
    private String email;

    @Basic
    @Size(max = 100)
    @Column(name = "name")
    private String name;

    @Basic
    @Size(max = 100)
    @Column(name = "last_name")
    private String lastName;

    @Basic
    @Size(max = 100)
    @Column(name = "second_last_name")
    private String secondLastName;

    @Basic
    @Size(max = 344)
    @Column(name = "token")
    private String token;

    @Basic
    @Column(name = "token_enable")
    private Boolean tokenEnable;

    @Basic
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_status", referencedColumnName = "id_user_status")
    private UserStatusEntity idUserStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_rol", referencedColumnName = "id_user_rol")
    private UserRolEntity idUserRol;
}
