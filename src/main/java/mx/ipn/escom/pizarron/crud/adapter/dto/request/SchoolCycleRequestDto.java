package mx.ipn.escom.pizarron.crud.adapter.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SchoolCycleRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer key;
    private String name;
}