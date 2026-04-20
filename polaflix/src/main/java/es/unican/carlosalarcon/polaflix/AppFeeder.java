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
        Persona helen = new Persona("Helen McCrory");
        Persona joe = new Persona("Joe Cole");

        Persona alberto = new Persona("Alberto Caballero");
        Persona laura = new Persona("Laura Caballero");
        Persona pablo = new Persona("Pablo Chiapella");
        Persona jordi = new Persona("Jordi Sánchez");
        Persona eva = new Persona("Eva Isanta");
        Persona nathalie = new Persona("Nathalie Seseña");
        Persona fernando = new Persona("Fernando Tejero");

        Persona paulScheuring = new Persona("Paul Scheuring");
        Persona wentworth = new Persona("Wentworth Miller");
        Persona dominic = new Persona("Dominic Purcell");
        Persona amaury = new Persona("Amaury Nolasco");
        Persona robert = new Persona("Robert Knepper");
        Persona sarah = new Persona("Sarah Wayne Callies");

        // ==========================================
        // 2. PEAKY BLINDERS (Categoría GOLD) - 4 Temporadas
        // ==========================================
        SerieGold peaky = new SerieGold("S01", "Peaky Blinders", "Una familia de gánsteres asienta su dominio en Birmingham tras la Primera Guerra Mundial, liderados por el ambicioso Tommy Shelby.", steven);
        peaky.getActores().addAll(Arrays.asList(cillian, paul, tom, helen, joe));

        for (int t = 1; t <= 4; t++) {
            Temporada temp = new Temporada(t);
            temp.addCapitulo(new Capitulo(1, "Episodio 1", "Los Peaky Blinders llaman la atención de un inspector tras un robo de armas."));
            temp.addCapitulo(new Capitulo(2, "Episodio 2", "Tommy hace un movimiento audaz en las carreras."));
            temp.addCapitulo(new Capitulo(3, "Episodio 3", "Nuevas alianzas y traiciones en el inframundo de Birmingham."));
            temp.addCapitulo(new Capitulo(4, "Episodio 4", "La guerra de bandas estalla con consecuencias letales."));
            temp.addCapitulo(new Capitulo(5, "Episodio 5", "Secretos del pasado amenazan con destruir a la familia."));
            temp.addCapitulo(new Capitulo(6, "Episodio 6", "Un final explosivo que cambiará el destino de los Shelby."));
            peaky.addTemporada(temp);
        }

        // ==========================================
        // 3. PRISON BREAK (Categoría SILVER) - 4 Temporadas
        // ==========================================
        SerieSilver prison = new SerieSilver("S02", "Prison Break", "Michael Scofield elabora un plan magistral para entrar en la prisión de Fox River y salvar a su hermano inocente del corredor de la muerte.", paulScheuring);
        prison.getActores().addAll(Arrays.asList(wentworth, dominic, amaury, robert, sarah));

        for (int t = 1; t <= 4; t++) {
            Temporada temp = new Temporada(t);
            temp.addCapitulo(new Capitulo(1, "Piloto", "Michael Scofield atraca un banco para que lo encierren en Fox River."));
            temp.addCapitulo(new Capitulo(2, "Allen", "Comienzan los preparativos para la fuga aprovechando el tatuaje."));
            temp.addCapitulo(new Capitulo(3, "Cell Test", "Michael pone a prueba la lealtad de su compañero de celda."));
            temp.addCapitulo(new Capitulo(4, "Cute Poison", "Complicaciones imprevistas obligan a cambiar el plan."));
            temp.addCapitulo(new Capitulo(5, "English, Fitz or Percy", "Una inspección sorpresa amenaza con descubrir el agujero."));
            temp.addCapitulo(new Capitulo(6, "Riots, Drills and the Devil", "Un motín carcelario sirve de tapadera perfecta."));
            prison.addTemporada(temp);
        }

        // ==========================================
        // 4. LA QUE SE AVECINA (Categoría ESTANDAR) - 4 Temporadas
        // ==========================================
        SerieEstandar lqsa = new SerieEstandar("S03", "La que se avecina", "Las disparatadas vidas y problemas cotidianos de los pintorescos vecinos de la comunidad de Mirador de Montepinar.", alberto);
        lqsa.getCreadores().add(laura);
        lqsa.getActores().addAll(Arrays.asList(pablo, jordi, eva, nathalie, fernando));

        for (int t = 1; t <= 4; t++) {
            Temporada temp = new Temporada(t);
            temp.addCapitulo(new Capitulo(1, "Mirador de Montepinar", "Los nuevos vecinos llegan al bloque con ilusión y muchas deudas."));
            temp.addCapitulo(new Capitulo(2, "Okupas, secuestros y un golpe", "El caos se apodera de las zonas comunes de la comunidad."));
            temp.addCapitulo(new Capitulo(3, "Mentiras, derramas y un moroso", "Antonio Recio intenta esquivar el pago de la última derrama."));
            temp.addCapitulo(new Capitulo(4, "Un chantaje, una exclusiva...", "Amador se enfrenta a su mayor crisis matrimonial."));
            temp.addCapitulo(new Capitulo(5, "Un tróspido, un mayorista...", "Las juntas de vecinos se vuelven un campo de batalla."));
            temp.addCapitulo(new Capitulo(6, "Una cigüeña, un león y un...", "Enredos amorosos y malentendidos en el rellano."));
            lqsa.addTemporada(temp);
        }

        // Guardamos todo el catálogo de golpe
        sr.saveAll(Arrays.asList(peaky, prison, lqsa));
    }

    private void feedUsuariosYVisualizaciones() {
        IBAN ibanPrueba = new IBAN("ES0011112222333344445555");

        Serie peaky = sr.findById("S01").orElseThrow();
        Serie prison = sr.findById("S02").orElseThrow();
        Serie lqsa = sr.findById("S03").orElseThrow();

        Capitulo peakyS1E1 = peaky.getTemporadas().get(0).getCapitulos().get(0);
        Capitulo peakyS1E2 = peaky.getTemporadas().get(0).getCapitulos().get(1);

        Capitulo prisonS1E1 = prison.getTemporadas().get(0).getCapitulos().get(0);

        Capitulo lqsaS1E1 = lqsa.getTemporadas().get(0).getCapitulos().get(0);
        Capitulo lqsaS3E1 = lqsa.getTemporadas().get(2).getCapitulos().get(0); // T3 E1

        // ==========================================
        // CARLOS ALARCON (Suscripción Tarifa Plana)
        // Viendo LQSA para relajarse y Peaky Blinders
        // ==========================================
        Usuario carlos = new Usuario("carlosalarcon", "pass123", ibanPrueba, new PlanSuscripcion(true, 20.0));
        carlos.verCapitulo(lqsaS1E1, null);
        carlos.verCapitulo(lqsaS3E1, null);
        carlos.verCapitulo(peakyS1E1, null);
        ur.save(carlos);

        Factura facCarlos = new Factura("F-CAR-001", carlos, LocalDate.now().getMonthValue(), LocalDate.now().getYear());
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
        fr.save(facCR7);

        // ==========================================
        // PEPE REINA (Suscripción Tarifa Plana)
        // Usuario que aún no ha visto nada este mes
        // ==========================================
        Usuario pepe = new Usuario("pepereina", "camarero", ibanPrueba, new PlanSuscripcion(true, 20.0));
        pepe.agregarSeriePendiente(prison);
        ur.save(pepe);

        Factura facPepe = new Factura("F-PEPE-001", pepe, LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        fr.save(facPepe);
    }
}