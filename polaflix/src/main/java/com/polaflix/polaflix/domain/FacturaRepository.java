package com.polaflix.domain;

import java.util.List;

public interface FacturaRepository {
    
    Factura findById(String id);
    
    Factura findByUsernameAndMesAndAnio(String username, int mes, int anio);
    
    List<Factura> findByUsernameOrderByAnioDescMesDesc(String username);
    
    void save(Factura factura);
}