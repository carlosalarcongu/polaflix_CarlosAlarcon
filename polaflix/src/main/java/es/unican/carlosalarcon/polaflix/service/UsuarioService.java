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

    @Transactional(readOnly = true)
    public Usuario login(String username, String contrasena) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new java.util.NoSuchElementException();
        }
        if (!contrasena.equals(usuario.getContrasenha())) {
            throw new SecurityException();
        }
        return usuario;
    }

    @Transactional
    public Usuario guardarOActualizarUsuario(String username, String contrasena, String ibanStr, boolean esTarifaPlana, double cuota) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        
        IBAN nuevoIban = new IBAN(ibanStr);
        PlanSuscripcion nuevoPlan = new PlanSuscripcion(esTarifaPlana, cuota);

        if (usuario == null) {
            usuario = new Usuario(username, contrasena, nuevoIban, nuevoPlan);
            usuarioRepository.save(usuario);
        } else {
            usuario.actualizarDatos(contrasena, nuevoIban, nuevoPlan);
        }
        
        return usuario;
    }

    @Transactional
    public void borrarUsuario(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario != null) {
            usuarioRepository.delete(usuario);
        } else {
            throw new IllegalArgumentException("El usuario a borrar no existe.");
        }
    }

    @Transactional
    public void cambiarContrasena(String username, String actual, String nueva) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) throw new IllegalArgumentException("Usuario no encontrado.");
        
        if (!usuario.getContrasenha().equals(actual)) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta.");
        }
        
        usuario.actualizarDatos(nueva, null, null);
    }

    @Transactional
    public void agregarSeriePendiente(String username, Integer serieId) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) throw new IllegalArgumentException("Usuario no encontrado.");
        
        Serie serie = serieRepository.findById(serieId)
                .orElseThrow(() -> new IllegalArgumentException("Serie no encontrada con ID: " + serieId));

        usuario.agregarSeriePendiente(serie);
    }

    @Transactional
    public void quitarSeriePendiente(String username, Integer serieId) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) throw new IllegalArgumentException("Usuario no encontrado.");
        
        Serie serie = serieRepository.findById(serieId)
                .orElseThrow(() -> new IllegalArgumentException("Serie no encontrada con ID: " + serieId));

        usuario.quitarSeriePendiente(serie);
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

        Factura factura = null;
        if (!usuario.getPlanSuscripcion().isTarifaPlana() && serie.getCosteVisionado() > 0) {
            int mesActual = LocalDate.now().getMonthValue();
            int anioActual = LocalDate.now().getYear();
            factura = facturaRepository.findByUsuarioUsernameAndMesAndAnio(username, mesActual, anioActual);
            
            if (factura == null) {
                String idFactura = "F-" + usuario.getUsername() + "-" + mesActual + "-" + anioActual;
                factura = new Factura(idFactura, usuario, mesActual, anioActual);
            }
        }

        usuario.verCapitulo(capitulo, factura);
        
        if (factura != null) {
            facturaRepository.save(factura);
        }
    }
}