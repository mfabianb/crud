package mx.ipn.escom.pizarron.crud.domain.api.service;

import mx.ipn.escom.pizarron.crud.domain.api.CrudServicePort;

public class CrudServiceImpl implements CrudServicePort {
    @Override
    public String saludar() {
        String saludo = "Hola desde spring. ";
        saludo += "Colaboradores: ";
        saludo += "Martin Fabian, ";
        saludo += "Emmanuel Mendez";
        return saludo;
    }
}
