package com.polaflix.infrastructure.repository;

import com.polaflix.domain.Usuario;
import com.polaflix.domain.UsuarioRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// @Repository indica a Spring que debe crear y gestionar una instancia de esta clase
@Repository
public class InMemoryUsuarioRepository implements UsuarioRepository {
    
    private final Map<String, Usuario> baseDatosUsuarios = new ConcurrentHashMap<>();

    @Override
    public Usuario findById(String username) {
        return baseDatosUsuarios.get(username);
    }

    @Override
    public void save(Usuario usuario) {
        baseDatosUsuarios.put(usuario.getUsername(), usuario);
    }

    @Override
    public void delete(String username) {
        baseDatosUsuarios.remove(username);
    }
}