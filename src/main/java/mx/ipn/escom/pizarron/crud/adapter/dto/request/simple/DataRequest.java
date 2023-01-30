package mx.ipn.escom.pizarron.crud.adapter.dto.request.simple;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DataRequest<D> extends SimpleRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private D data;

    DataRequest(D data, String sort){
        super(sort);
        this.data = data;
    }

    DataRequest(D data, Integer page, Integer size, String order, String sort){
        super(page, size, order, sort);
        this.data = data;
    }
}
