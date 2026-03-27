package es.unican.carlosalarcon.polaflix.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Spring Boot generará automáticamente el código de esta consulta por nosotros
    Usuario findByUsername(String username);
}