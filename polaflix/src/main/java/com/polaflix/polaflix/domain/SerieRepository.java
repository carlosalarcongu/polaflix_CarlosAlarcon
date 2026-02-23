package com.polaflix.domain;

import java.util.List;

public interface SerieRepository {
    
    Serie findById(String id);
    
    List<Serie> findByInicialOrderByTituloAsc(char inicial);
    
    List<Serie> findByTituloContainingIgnoreCase(String titulo);
    
    void save(Serie serie);
    
    List<Serie> findAll();
}