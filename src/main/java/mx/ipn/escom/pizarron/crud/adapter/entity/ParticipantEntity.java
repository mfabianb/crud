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
@Table(name = "participant")
@XmlRootElement
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ParticipantEntity implements Serializable {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "id_participant")
    @Size(max = 36)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String idParticipant;

    @Basic
    @Column(name = "enrollment_date")
    private LocalDateTime enrollmentDate;

    @Basic
    @Column(name = "last_online")
    private LocalDateTime lastOnline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_class", referencedColumnName = "id_class")
    private ClassEntity idClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private UserEntity idUser;

    @Basic(optional = false)
    @NotNull
    @Column(name = "enrolled")
    private Boolean enrolled;

    @Basic(optional = false)
    @NotNull
    @Column(name = "pending")
    private Boolean pending;

    @Basic(optional = false)
    @NotNull
    @Column(name = "owner")
    private Boolean owner;

}
