package es.unican.carlosalarcon.polaflix.domain;

public class LoginDTO {
    
    private String username;
    private String contrasena;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}