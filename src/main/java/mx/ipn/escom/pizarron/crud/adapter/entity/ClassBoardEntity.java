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
@Table(name = "class_board")
@XmlRootElement
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClassBoardEntity implements Serializable {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "id_class_board")
    @Size(max = 36)
    private String idClassBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_class", referencedColumnName = "id_class")
    private ClassEntity idClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_board", referencedColumnName = "id_board")
    private BoardEntity idBoard;

}
