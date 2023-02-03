package mx.ipn.escom.pizarron.crud.adapter.dto.request.simple;

import lombok.Data;

@Data
public class SimpleRequest {
    private Integer page;
    private Integer size;
    private String sort;
    private String order;

    SimpleRequest(){
        this.page = 0;
        this.size = 10;
        this.order = "DESC";
    }

    SimpleRequest(String sort){
        this.page = 0;
        this.size = 10;
        this.order = "DESC";
        this.sort = sort;
    }

    SimpleRequest(Integer page, Integer size, String order, String sort){
        this.page = page;
        this.size = size;
        this.order = order;
        this.sort = sort;
    }
}
