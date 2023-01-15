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
import java.util.UUID;

@Entity
@Data
@Table(name = "group_subject")
@XmlRootElement
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GroupSubjectEntity implements Serializable {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "id_group_subject")
    private UUID idGroupSubject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_group", referencedColumnName = "id_group")
    private GroupEntity idGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subject", referencedColumnName = "id_subject")
    private SubjectEntity idSubject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_school_cycle", referencedColumnName = "id_school_cycle")
    private SchoolCycleEntity idSchoolCycle;


}
