package com.polaflix.domain;

import java.util.Optional;

public interface UsuarioRepository {
    
    Usuario findById(String username);
    
    void save(Usuario usuario);
    
    void delete(String username);
}