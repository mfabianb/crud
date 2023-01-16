package mx.ipn.escom.pizarron.crud.adapter.dto.request.simple;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DataRequest<D> extends SimpleRequest {
    private D data;

    DataRequest(D data){
        super();
        this.data = data;
    }

    DataRequest(D data, String sort){
        super(sort);
        this.data = data;
    }

    DataRequest(D data, Integer page, Integer size, String order, String sort){
        super(page, size, order, sort);
        this.data = data;
    }
}
