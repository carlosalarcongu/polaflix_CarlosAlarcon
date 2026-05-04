package es.unican.carlosalarcon.polaflix.domain;

public class UsuarioDTO {
    
    private String contrasena;
    private String iban;
    private boolean esTarifaPlana;
    private double cuota;

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }
    public boolean isEsTarifaPlana() { return esTarifaPlana; }
    public void setEsTarifaPlana(boolean esTarifaPlana) { this.esTarifaPlana = esTarifaPlana; }
    public double getCuota() { return cuota; }
    public void setCuota(double cuota) { this.cuota = cuota; }
}