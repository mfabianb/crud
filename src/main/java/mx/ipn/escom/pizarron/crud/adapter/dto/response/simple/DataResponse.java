package mx.ipn.escom.pizarron.crud.adapter.dto.response.simple;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DataResponse<D> extends SimpleResponse {

    private D data;

    public DataResponse(D data){
        super();
        this.data = data;
    }

    public DataResponse(D data, String message){
        super(message);
        this.data = data;
    }

    public DataResponse(D data, Boolean success, Integer code, String message){
        super(success, code, message);
        this.data = data;
    }
}
