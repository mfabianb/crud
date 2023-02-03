package mx.ipn.escom.pizarron.crud.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Locale;

public class CreatePageable {
    private Pageable pageable;

    public CreatePageable(Integer page, Integer size, String sort, String order){
        if(order.toUpperCase(Locale.ROOT).equals("DESC")) pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        else pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
    }

    public Pageable getPageable(){
        return pageable;
    }
}
