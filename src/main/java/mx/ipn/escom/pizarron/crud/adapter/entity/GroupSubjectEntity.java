package mx.ipn.escom.pizarron.crud.adapter.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Size(max = 36)
    private String idGroupSubject;

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
