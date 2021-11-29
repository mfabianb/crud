package mx.ipn.escom.pizarron.crud.domain.api.service;

import mx.ipn.escom.pizarron.crud.domain.api.CrudServicePort;
import mx.ipn.escom.pizarron.crud.domain.data.dto.SaludoDto;

public class CrudServiceImpl implements CrudServicePort {
    @Override
    public SaludoDto saludar() {
        SaludoDto sss = new SaludoDto();

        String saludo = "Hola desde spring. ";
        saludo += "Colaboradores: ";
        saludo += "Martin Fabian, ";
        saludo += "Emmanuel Mendez. ";
        sss.setMessage("{\"message\": \"" + saludo + "\"}");
        //saludo += "Aquí va haber un TT bien chido.";
        //saludo += "Aquí va haber un TT bien chido.";
        return sss;
    }
}
