package mx.ipn.escom.pizarron.crud.adapter.dto.response.simple;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class SimpleResponse {
    private Boolean success;
    private String message;
    private Integer code;

    public SimpleResponse(){
        this.success = true;
        this.code = HttpStatus.OK.value();
        this.message = "OK";
    }

    public SimpleResponse(String message){
        this.success = true;
        this.code = HttpStatus.OK.value();
        this.message = message;
    }

    public SimpleResponse(Boolean success, Integer code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
