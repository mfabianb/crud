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
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "page_draw")
@XmlRootElement
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PageDrawEntity implements Serializable {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "id_page_draw")
    private UUID idPageDraw;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_page", referencedColumnName = "id_page")
    private PageEntity idPage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_draw", referencedColumnName = "id_draw")
    private DrawEntity idDraw;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private UserEntity idUser;

    @Basic
    @NotNull
    @Column(name = "last_modification")
    private LocalDateTime lastModification;

}
