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
@Table(name = "subject")
@XmlRootElement
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SubjectEntity implements Serializable {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "id_subject")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idSubject;

    @Basic(optional = false)
    @NotNull
    @Size(max = 45)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @NotNull
    @Column(name = "enable")
    private Boolean enable;
}
