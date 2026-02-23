package com.polaflix.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
    private String username; 
    private String contrasena;
    private IBAN iban;
    private PlanSuscripcion planSuscripcion;
    
    private List<String> seriesPendientes;
    private List<String> seriesEmpezadas;
    private List<String> seriesTerminadas;
    private List<RegistroVisualizacion> historialVisualizaciones;

    public Usuario(String username, String contrasena, IBAN iban, PlanSuscripcion planSuscripcion) {
        this.username = username;
        this.contrasena = contrasena;
        this.iban = iban;
        this.planSuscripcion = planSuscripcion;
        this.seriesPendientes = new ArrayList<>();
        this.seriesEmpezadas = new ArrayList<>();
        this.seriesTerminadas = new ArrayList<>();
        this.historialVisualizaciones = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(username, usuario.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public PlanSuscripcion getPlanSuscripcion() {
        return planSuscripcion;
    }

    // Getters y Setters ... TODO

    public void agregarSeriePendiente(String serieId) {
        if (!seriesPendientes.contains(serieId) && 
            !seriesEmpezadas.contains(serieId) && 
            !seriesTerminadas.contains(serieId)) {
            
            this.seriesPendientes.add(serieId);
        }
    }

    public void registrarVisualizacionANivelUsuario(String serieId, int numTemporada, int numCapitulo, boolean esUltimoCapituloSerie) {
        
        this.historialVisualizaciones.add(new RegistroVisualizacion(serieId, numTemporada, numCapitulo, LocalDate.now()));

        this.seriesPendientes.remove(serieId); 

        if (!this.seriesEmpezadas.contains(serieId) && !this.seriesTerminadas.contains(serieId)) {
            this.seriesEmpezadas.add(serieId);
        }

        if (esUltimoCapituloSerie) {
            this.seriesEmpezadas.remove(serieId); // La quitamos de empezadas
            if (!this.seriesTerminadas.contains(serieId)) {
                this.seriesTerminadas.add(serieId); // La movemos a terminadas
            }
        }
    }
}