package es.unican.carlosalarcon.polaflix;

import es.unican.carlosalarcon.polaflix.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AppFeeder implements CommandLineRunner {
    
    @Autowired
    protected UsuarioRepository ur;
    @Autowired
    protected SerieRepository sr;

    @Override
    public void run(String... args) throws Exception {
        feedSeries();
        feedUsuarios();

    }

    private void feedSeries() {
        SerieGold got = new SerieGold("S01", "Juego de Tronos", "Fantasía épica medieval.");
        got.getActores().addAll(Arrays.asList("Nicolas", "AJ", "Peter Dinklage"));
        
        SerieEstandar serrano = new SerieEstandar("S02", "Los Serrano", "Comedia española.");
        
        // Añadir una temporada y un capítulo de prueba a GOT
        Temporada t1 = new Temporada(1);
        t1.getCapitulos().add(new Capitulo(1, "Se acerca el invierno", "Piloto de la serie."));
        got.getTemporadas().add(t1);

        sr.save(got);
        sr.save(serrano);
    }

    private void feedUsuarios() {
        IBAN ibanPrueba = new IBAN("ES1234567890123456789012");
        
        Usuario paco = new Usuario("pacopolaciones", "1234", ibanPrueba, new PlanSuscripcion(false, 0.0));
        Usuario lola = new Usuario("lolapolaciones", "1234", ibanPrueba, new PlanSuscripcion(true, 20.0));
        Usuario maxim = new Usuario("maxim", "pass", ibanPrueba, new PlanSuscripcion(false, 0.0));
        
        ur.save(paco);
        ur.save(lola);
        ur.save(maxim);
    }
}