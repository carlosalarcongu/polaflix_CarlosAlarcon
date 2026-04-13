package es.unican.carlosalarcon.polaflix.domain;

public class Views {
    
    // Vista básica: Ideal para devolver listas grandes sin saturar la red
    public static interface SerieResumida {}
    
    // Vista profunda: Hereda de la resumida (incluye lo anterior) y añade detalles pesados
    public static interface SerieDetallada extends SerieResumida {}
    
    // Vista para el perfil del usuario
    public static interface UsuarioBasico {}
}