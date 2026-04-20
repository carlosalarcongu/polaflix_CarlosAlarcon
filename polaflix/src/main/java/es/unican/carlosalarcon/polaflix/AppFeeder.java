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
    protected FacturaRepository fr;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        feedSeries();
        feedUsuariosYVisualizaciones();
        
        System.out.println("===================================================================");
        System.out.println("✅ Base de datos de Polaflix poblada con MEGA catálogo VIP, usuarios y facturas.");
        System.out.println("===================================================================");
    }

    private void feedSeries() {
        // ==========================================
        // 1. ELENCO DE TALENTO (Personas)
        // ==========================================
        Persona steven = new Persona("Steven Knight");
        Persona cillian = new Persona("Cillian Murphy");
        Persona paul = new Persona("Paul Anderson");
        Persona tom = new Persona("Tom Hardy");

        Persona alberto = new Persona("Alberto Caballero");
        Persona laura = new Persona("Laura Caballero");
        Persona pablo = new Persona("Pablo Chiapella");
        Persona jordi = new Persona("Jordi Sánchez");

        Persona paulScheuring = new Persona("Paul Scheuring");
        Persona wentworth = new Persona("Wentworth Miller");
        Persona dominic = new Persona("Dominic Purcell");

        // ==========================================
        // 2. PEAKY BLINDERS (Categoría GOLD)
        // ==========================================
        SerieGold peaky = new SerieGold("S01", "Peaky Blinders", "Familia de gánsteres en Birmingham tras la Primera Guerra Mundial.", steven);
        peaky.getActores().addAll(Arrays.asList(cillian, paul, tom));
        
        Temporada pbT1 = new Temporada(1);
        pbT1.addCapitulo(new Capitulo(1, "Episodio 1", "Thomas Shelby encuentra armas perdidas."));
        pbT1.addCapitulo(new Capitulo(2, "Episodio 2", "La policía presiona a los Peaky Blinders."));
        
        Temporada pbT2 = new Temporada(2);
        pbT2.addCapitulo(new Capitulo(1, "Episodio 1", "Los Shelby se expanden a Londres."));
        pbT2.addCapitulo(new Capitulo(2, "Episodio 2", "Nuevos enemigos italianos."));

        Temporada pbT6 = new Temporada(6); // Saltamos a la última para simular catálogo extenso
        pbT6.addCapitulo(new Capitulo(1, "Día Negro", "Las consecuencias del atentado fallido."));

        peaky.addTemporada(pbT1);
        peaky.addTemporada(pbT2);
        peaky.addTemporada(pbT6);

        // ==========================================
        // 3. PRISON BREAK (Categoría SILVER)
        // ==========================================
        SerieSilver prison = new SerieSilver("S02", "Prison Break", "Un hombre entra a la cárcel para salvar a su hermano condenado a muerte.", paulScheuring);
        prison.getActores().addAll(Arrays.asList(wentworth, dominic));
        
        Temporada prT1 = new Temporada(1);
        prT1.addCapitulo(new Capitulo(1, "Piloto", "Michael atraca un banco para entrar en Fox River."));
        prT1.addCapitulo(new Capitulo(2, "Allen", "Comienzan los preparativos de la fuga."));
        prT1.addCapitulo(new Capitulo(3, "Cell Test", "Prueba de las tijeras en la celda."));
        
        Temporada prT2 = new Temporada(2);
        prT2.addCapitulo(new Capitulo(1, "Manhunt", "Comienza la cacería tras la fuga."));
        
        prison.addTemporada(prT1);
        prison.addTemporada(prT2);

        // ==========================================
        // 4. LA QUE SE AVECINA (Categoría ESTANDAR)
        // ==========================================
        SerieEstandar lqsa = new SerieEstandar("S03", "La que se avecina", "Aventuras y locuras de los vecinos de Mirador de Montepinar.", alberto);
        lqsa.getCreadores().add(laura); // Co-creadora
        lqsa.getActores().addAll(Arrays.asList(pablo, jordi));
        
        Temporada lqsaT1 = new Temporada(1);
        lqsaT1.addCapitulo(new Capitulo(1, "Mirador de Montepinar", "Llegan los primeros vecinos al edificio."));
        lqsaT1.addCapitulo(new Capitulo(2, "Okupas, secuestros y un golpe", "Caos en las zonas comunes."));
        
        Temporada lqsaT10 = new Temporada(10);
        lqsaT10.addCapitulo(new Capitulo(1, "Un tróspido, un mayorista...", "Fermín Trujillo asume la presidencia."));
        
        Temporada lqsaT13 = new Temporada(13); // Última temporada
        lqsaT13.addCapitulo(new Capitulo(1, "Contubernio 49", "Mudanza al nuevo edificio céntrico."));

        lqsa.addTemporada(lqsaT1);
        lqsa.addTemporada(lqsaT10);
        lqsa.addTemporada(lqsaT13);

        // Guardamos todo el catálogo de golpe
        sr.saveAll(Arrays.asList(peaky, prison, lqsa));
    }

    private void feedUsuariosYVisualizaciones() {
        IBAN ibanPrueba = new IBAN("ES0011112222333344445555");
        
        // Recuperamos las series de BBDD
        Serie peaky = sr.findById("S01").orElseThrow();
        Serie prison = sr.findById("S02").orElseThrow();
        Serie lqsa = sr.findById("S03").orElseThrow();

        // Extraemos algunos capítulos de muestra
        Capitulo peakyS1E1 = peaky.getTemporadas().get(0).getCapitulos().get(0);
        Capitulo peakyS1E2 = peaky.getTemporadas().get(0).getCapitulos().get(1);
        
        Capitulo prisonS1E1 = prison.getTemporadas().get(0).getCapitulos().get(0);
        
        Capitulo lqsaS1E1 = lqsa.getTemporadas().get(0).getCapitulos().get(0);
        Capitulo lqsaS10E1 = lqsa.getTemporadas().get(1).getCapitulos().get(0); // T10 E1

        // ==========================================
        // CARLOS ALARCON (Suscripción Tarifa Plana)
        // Viendo LQSA para relajarse y Peaky Blinders
        // ==========================================
        Usuario carlos = new Usuario("carlosalarcon", "pass123", ibanPrueba, new PlanSuscripcion(true, 20.0));
        carlos.verCapitulo(lqsaS1E1, null); 
        carlos.verCapitulo(lqsaS10E1, null);
        carlos.verCapitulo(peakyS1E1, null);
        ur.save(carlos);

        Factura facCarlos = new Factura("F-CAR-001", carlos, LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        // facCarlos.anadirCargo(new LineaFactura(LocalDate.now(), "Cuota Mensual Tarifa Plana VIP", "N/A", carlos.getPlanSuscripcion().getCuotaMensual()));
        fr.save(facCarlos);

        // ==========================================
        // MARIANO RAJOY (Pago por Visión - Básico)
        // Ha visto un poco de Prison Break y luego lo dejó
        // ==========================================
        Usuario mariano = new Usuario("mrajoy", "finfin", ibanPrueba, new PlanSuscripcion(false, 0.0));
        mariano.verCapitulo(prisonS1E1, null);
        mariano.agregarSeriePendiente(lqsa); // La añade a su lista para verla después
        ur.save(mariano);

        Factura facMariano = new Factura("F-MR-001", mariano, LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        // facMariano.anadirCargo(new LineaFactura(LocalDate.now(), prison.getTitulo(), "T1xC1", prison.getCosteVisionado()));
        fr.save(facMariano);

        // ==========================================
        // CRISTIANO RONALDO (Pago por Visión - Consumidor Elite)
        // Enganchado a Peaky Blinders
        // ==========================================
        Usuario cristiano = new Usuario("cr7bicho", "siiii", ibanPrueba, new PlanSuscripcion(false, 0.0));
        cristiano.verCapitulo(peakyS1E1, null);
        cristiano.verCapitulo(peakyS1E2, null);
        ur.save(cristiano);

        Factura facCR7 = new Factura("F-CR7-001", cristiano, LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        // facCR7.anadirCargo(new LineaFactura(LocalDate.now(), peaky.getTitulo(), "T1xC1", peaky.getCosteVisionado()));
        // facCR7.anadirCargo(new LineaFactura(LocalDate.now(), peaky.getTitulo(), "T1xC2", peaky.getCosteVisionado()));
        fr.save(facCR7);

        // ==========================================
        // PEPE REINA (Suscripción Tarifa Plana)
        // Usuario que aún no ha visto nada este mes
        // ==========================================
        Usuario pepe = new Usuario("pepereina", "camarero", ibanPrueba, new PlanSuscripcion(true, 20.0));
        pepe.agregarSeriePendiente(prison);
        ur.save(pepe);

        Factura facPepe = new Factura("F-PEPE-001", pepe, LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        // facPepe.anadirCargo(new LineaFactura(LocalDate.now(), "Cuota Mensual Tarifa Plana", "N/A", pepe.getPlanSuscripcion().getCuotaMensual()));
        fr.save(facPepe);
    }
}