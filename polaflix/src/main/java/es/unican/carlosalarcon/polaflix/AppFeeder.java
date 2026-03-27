package es.unican.carlosalarcon.polaflix;

import es.unican.carlosalarcon.polaflix.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
        Persona george = new Persona("George R.R. Martin");
        Persona nicolas = new Persona("Nicolas");
        Persona aj = new Persona("AJ");
        Persona dinklage = new Persona("Peter Dinklage");
        Persona pina = new Persona("Alex Pina");

        SerieGold got = new SerieGold("S01", "Juego de Tronos", "Fantasía épica.", george);
        got.getActores().add(nicolas);
        got.getActores().add(aj);
        got.getActores().add(dinklage);
        
        SerieEstandar serrano = new SerieEstandar("S02", "Los Serrano", "Comedia española.", pina);
        
        Temporada t1 = new Temporada(1);
        t1.addCapitulo(new Capitulo(1, "Se acerca el invierno", "Piloto."));
        got.addTemporada(t1); // Uso de la relación bidireccional

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