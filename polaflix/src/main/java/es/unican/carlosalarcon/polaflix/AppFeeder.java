package es.unican.carlosalarcon.polaflix;

import es.unican.carlosalarcon.polaflix.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class AppFeeder implements CommandLineRunner {
    
    @Autowired
    protected UsuarioRepository ur;
    
    @Autowired
    protected SerieRepository sr;

    @Autowired
    protected FacturaRepository fr; // Añadido para guardar las facturas generadas

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // El orden es importante: primero series (recursos), luego usuarios (clientes), luego facturas
        feedSeries();
        feedUsuariosYVisualizaciones();
        
        System.out.println("===================================================================");
        System.out.println("✅ Base de datos de Polaflix poblada con catálogo, usuarios y facturas.");
        System.out.println("===================================================================");
    }

    private void feedSeries() {
        // 1. Crear el talento (Personas)
        Persona george = new Persona("George R.R. Martin");
        Persona nicolas = new Persona("Nicolas");
        Persona aj = new Persona("AJ");
        Persona dinklage = new Persona("Peter Dinklage");
        
        Persona pina = new Persona("Alex Pina");
        Persona resines = new Persona("Antonio Resines");
        
        Persona vince = new Persona("Vince Gilligan");
        Persona bryan = new Persona("Bryan Cranston");
        Persona aaron = new Persona("Aaron Paul");

        // 2. Crear Series GOLD
        SerieGold got = new SerieGold("S01", "Juego de Tronos", "Fantasía épica y traiciones.", george);
        got.getActores().addAll(Arrays.asList(nicolas, aj, dinklage));
        
        Temporada gotT1 = new Temporada(1);
        gotT1.addCapitulo(new Capitulo(1, "Se acerca el invierno", "El rey visita Invernalia."));
        gotT1.addCapitulo(new Capitulo(2, "El Camino Real", "Jon Nieve viaja al muro."));
        Temporada gotT2 = new Temporada(2);
        gotT2.addCapitulo(new Capitulo(1, "El Norte recuerda", "Robb Stark en guerra."));
        
        got.addTemporada(gotT1);
        got.addTemporada(gotT2);

        // 3. Crear Series SILVER
        SerieSilver bb = new SerieSilver("S02", "Breaking Bad", "Profesor de química cambia de vida.", vince);
        bb.getActores().addAll(Arrays.asList(bryan, aaron));
        
        Temporada bbT1 = new Temporada(1);
        bbT1.addCapitulo(new Capitulo(1, "Piloto", "El diagnóstico de Walter."));
        bbT1.addCapitulo(new Capitulo(2, "El gato está en la bolsa", "Problemas con Krazy-8."));
        
        bb.addTemporada(bbT1);

        // 4. Crear Series ESTANDAR
        SerieEstandar serrano = new SerieEstandar("S03", "Los Serrano", "Comedia sobre una familia ensamblada.", pina);
        serrano.getActores().add(resines);
        
        Temporada serT1 = new Temporada(1);
        serT1.addCapitulo(new Capitulo(1, "Ya s'han casao", "La boda de Diego y Lucía."));
        serT1.addCapitulo(new Capitulo(2, "Un padre perfecto", "Conflictos en casa."));
        
        serrano.addTemporada(serT1);

        // 5. Guardar en el repositorio (La cascada JPA guardará temporadas, capítulos y personas automáticamente)
        sr.saveAll(Arrays.asList(got, bb, serrano));
    }

    private void feedUsuariosYVisualizaciones() {
        IBAN ibanPrueba = new IBAN("ES1234567890123456789012");
        
        // Recuperamos las series de la base de datos para simular las visualizaciones
        Serie got = sr.findById("S01").orElseThrow();
        Serie bb = sr.findById("S02").orElseThrow();
        Serie serrano = sr.findById("S03").orElseThrow();

        // Extraemos capítulos concretos para que los vean
        Capitulo gotS1E1 = got.getTemporadas().get(0).getCapitulos().get(0);
        Capitulo bbS1E1 = bb.getTemporadas().get(0).getCapitulos().get(0);
        Capitulo bbS1E2 = bb.getTemporadas().get(0).getCapitulos().get(1);
        Capitulo serS1E1 = serrano.getTemporadas().get(0).getCapitulos().get(0);

        // --- USUARIO 1: PACO (Pago por Visión) ---
        Usuario paco = new Usuario("pacopolaciones", "1234", ibanPrueba, new PlanSuscripcion(false, 0.0));
        paco.agregarSeriePendiente(got); // Añade Juego de Tronos a su lista para verla en el futuro
        paco.verCapitulo(bbS1E1); // Ve el primer capítulo de Breaking Bad (Pasa a EMPEZADA)
        paco.verCapitulo(bbS1E2); // Ve el segundo capítulo de Breaking Bad
        ur.save(paco);
        
        // Generar Factura de Pago por Visión para Paco (Mes actual)
        Factura facPaco = new Factura("F-PACO-001", paco.getUsername(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        facPaco.anadirCargo(new LineaFactura(LocalDate.now(), bb.getTitulo(), "T1xC1", bb.getCosteVisionado()));
        facPaco.anadirCargo(new LineaFactura(LocalDate.now(), bb.getTitulo(), "T1xC2", bb.getCosteVisionado()));
        fr.save(facPaco);

        // --- USUARIO 2: LOLA (Tarifa Plana) ---
        Usuario lola = new Usuario("lolapolaciones", "1234", ibanPrueba, new PlanSuscripcion(true, 20.0));
        lola.verCapitulo(gotS1E1); // Ve Juego de Tronos
        ur.save(lola);

        // Generar Factura de Tarifa Plana para Lola
        Factura facLola = new Factura("F-LOLA-001", lola.getUsername(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        facLola.anadirCargo(new LineaFactura(LocalDate.now(), "Cuota Mensual Tarifa Plana", "N/A", lola.getPlanSuscripcion().getCuotaMensual()));
        // (Nota: No se le cobran los visionados individuales porque tiene tarifa plana)
        fr.save(facLola);

        // --- USUARIO 3: MAXIM (Pago por Visión - Gran Consumidor) ---
        Usuario maxim = new Usuario("maxim", "pass", ibanPrueba, new PlanSuscripcion(false, 0.0));
        maxim.verCapitulo(serS1E1); // Ve Los Serrano
        maxim.verCapitulo(gotS1E1); // Ve Juego de Tronos
        ur.save(maxim);

        // Generar Factura para Maxim
        Factura facMaxim = new Factura("F-MAX-001", maxim.getUsername(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        facMaxim.anadirCargo(new LineaFactura(LocalDate.now(), serrano.getTitulo(), "T1xC1", serrano.getCosteVisionado()));
        facMaxim.anadirCargo(new LineaFactura(LocalDate.now(), got.getTitulo(), "T1xC1", got.getCosteVisionado()));
        fr.save(facMaxim);
    }
}