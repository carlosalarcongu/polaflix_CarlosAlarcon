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
    public void agregarSeriePendiente(String username, String serieId) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        Serie serie = serieRepository.findById(serieId)
                .orElseThrow(() -> new IllegalArgumentException("Serie no encontrada con ID: " + serieId));

        usuario.agregarSeriePendiente(serie);
    }

    @Transactional
    public void verCapitulo(String username, Long idCapitulo) {
        
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) throw new IllegalArgumentException("Usuario no encontrado: " + username);

        Serie serie = serieRepository.findSerieByCapituloId(idCapitulo)
                .orElseThrow(() -> new IllegalArgumentException("Capítulo no encontrado con ID: " + idCapitulo));

                
        Capitulo capitulo = serie.getTemporadas().stream()
                .flatMap(t -> t.getCapitulos().stream())
                .filter(c -> c.getId().equals(idCapitulo))
                .findFirst().orElseThrow();

                
        usuario.verCapitulo(capitulo);
        
        if (!usuario.getPlanSuscripcion().isTarifaPlana() && serie.getCosteVisionado() > 0) {
            int mesActual = LocalDate.now().getMonthValue();
            int anioActual = LocalDate.now().getYear();

            
            Factura factura = facturaRepository.findByUsernameAndMesAndAnio(username, mesActual, anioActual);
            
            


            if (factura == null) {
                String idFactura = "F-" + username + "-" + mesActual + "-" + anioActual;
                factura = new Factura(idFactura, username, mesActual, anioActual);
            }

            String tempCap = "T" + capitulo.getTemporada().getNumero() + "xC" + capitulo.getNumero();
            factura.anadirCargo(new LineaFactura(LocalDate.now(), serie.getTitulo(), tempCap, serie.getCosteVisionado()));
            facturaRepository.save(factura);
        }
    }
}