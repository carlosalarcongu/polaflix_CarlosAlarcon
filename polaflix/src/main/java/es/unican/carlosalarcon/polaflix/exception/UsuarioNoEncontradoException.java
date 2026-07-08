package es.unican.carlosalarcon.polaflix.exception;

public class UsuarioNoEncontradoException extends RuntimeException {
    
    public UsuarioNoEncontradoException(String username) {
        super("El usuario '" + username + "' no existe en el sistema.");
    }
    
}