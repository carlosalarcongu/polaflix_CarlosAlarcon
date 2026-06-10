package es.unican.carlosalarcon.polaflix.service;

import es.unican.carlosalarcon.polaflix.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Transactional(readOnly = true)
    public Usuario obtenerUsuario(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Transactional
    public void verCapitulo(String username, Long idCapitulo) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) throw new java.util.NoSuchElementException();

        Serie serie = serieRepository.findSerieByCapituloId(idCapitulo)
                .orElseThrow(() -> new java.util.NoSuchElementException());
                
        Capitulo capitulo = serie.getTemporadas().stream()
                .flatMap(t -> t.getCapitulos().stream())
                .filter(c -> c.getId().equals(idCapitulo))
                .findFirst().orElseThrow();

        usuario.verCapitulo(capitulo);
        usuarioRepository.save(usuario);
    }
}