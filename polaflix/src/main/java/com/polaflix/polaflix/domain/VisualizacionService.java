package com.polaflix.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

// @Service indica a Spring que esta clase contiene lógica de negocio
// y debe ser registrada en el contenedor de dependencias
@Service
public class VisualizacionService {
    
    // Estos repositorios simulan el acceso a la capa de persistencia
    private final UsuarioRepository usuarioRepository;
    private final SerieRepository serieRepository;
    private final FacturaRepository facturaRepository;

    public VisualizacionService(UsuarioRepository usuarioRepo, SerieRepository serieRepo, FacturaRepository facturaRepo) {
        this.usuarioRepository = usuarioRepo;
        this.serieRepository = serieRepo;
        this.facturaRepository = facturaRepo;
    }

    public void anotarCapituloReproducido(String username, String serieId, int numTemp, int numCap) {
        // 1. Recuperar los Aggregate Roots
        Usuario usuario = usuarioRepository.findById(username);
        Serie serie = serieRepository.findById(serieId);
        
        if (usuario == null || serie == null) {
            throw new IllegalArgumentException("Usuario o Serie no encontrados");
        }

        // 2. Determinar si es el último capítulo de la serie (Regla de negocio)
        boolean esUltimoCapitulo = serie.esUltimoCapitulo(numTemp, numCap); 

        // 3. Actualizar el estado del Usuario
        usuario.registrarVisualizacionANivelUsuario(serieId, numTemp, numCap, esUltimoCapitulo);

        // 4. Gestionar la facturación
        Factura facturaMesActual = facturaRepository.findByUsernameAndMesAndAnio(
            username, LocalDate.now().getMonthValue(), LocalDate.now().getYear()
        );

        // Si no existe factura para este mes, se crea una nueva (Responsabilidad del sistema)
        if (facturaMesActual == null) {
            facturaMesActual = new Factura("F-" + username + "-" + LocalDate.now().getMonthValue(), 
                                           username, LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        }

        // Calcular cargo (Si tiene tarifa plana, el cargo unitario a añadir es 0.0)
        double cargo = usuario.getPlanSuscripcion().isTarifaPlana() ? 0.0 : serie.getCategoria().getCosteVisionado();
        
        String formatoTemporadaCapitulo = "T" + numTemp + " E" + numCap;
        LineaFactura nuevaLinea = new LineaFactura(LocalDate.now(), serie.getTitulo(), formatoTemporadaCapitulo, cargo);
        
        facturaMesActual.anadirCargo(nuevaLinea);

        // 5. Persistir los cambios (En un entorno real, Spring manejaría la transacción aquí)
        usuarioRepository.save(usuario);
        facturaRepository.save(facturaMesActual);
    }
}