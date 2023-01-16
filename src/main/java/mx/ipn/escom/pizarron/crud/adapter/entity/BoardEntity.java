package mx.ipn.escom.pizarron.crud.adapter.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

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
@Table(name = "board")
@XmlRootElement
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BoardEntity implements Serializable {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "id_board")
    @Size(max = 36)
    private String idBoard;

    @Basic
    @NotNull
    @Size(max = 45)
    @Column(name = "name")
    private String name;

    @Basic
    @Size(max = 100)
    @Column(name = "description")
    private String description;

    @Basic
    @NotNull
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
}
