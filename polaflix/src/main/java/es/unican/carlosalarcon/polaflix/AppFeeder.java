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
        // ==========================================
        // 2. PEAKY BLINDERS (Categoría GOLD) - 4 Temporadas (Fiel a la serie)
        // ==========================================
        SerieGold peaky = new SerieGold("S01", "Peaky Blinders", "Una familia de gánsteres asienta su dominio en Birmingham tras la Primera Guerra Mundial, liderados por el ambicioso Tommy Shelby.", steven);
        peaky.getActores().addAll(Arrays.asList(cillian, paul, tom, helen, joe));

        // --- TEMPORADA 1 ---
        Temporada pbT1 = new Temporada(1);
        pbT1.addCapitulo(new Capitulo(1, "Episodio 1", "Thomas Shelby encuentra por accidente un cargamento de armas del gobierno, atrayendo la atención del implacable inspector Campbell."));
        pbT1.addCapitulo(new Capitulo(2, "Episodio 2", "Tommy desafía a los corredores de apuestas locales al amañar una carrera de caballos, provocando la ira de Billy Kimber."));
        pbT1.addCapitulo(new Capitulo(3, "Episodio 3", "Thomas asiste a las carreras de Cheltenham para forjar alianzas. Mientras, Ada y Freddie deciden casarse en secreto."));
        pbT1.addCapitulo(new Capitulo(4, "Episodio 4", "Para mantener la paz con la familia Lee, Tommy negocia el matrimonio de su hermano John con Esme."));
        pbT1.addCapitulo(new Capitulo(5, "Episodio 5", "El IRA visita Birmingham para comprar las armas perdidas. Tommy y Grace se acercan cada vez más."));
        pbT1.addCapitulo(new Capitulo(6, "Episodio 6", "Estalla el enfrentamiento final entre los Peaky Blinders y los hombres de Billy Kimber. Grace y Campbell tienen un encuentro letal."));
        peaky.addTemporada(pbT1);

        // --- TEMPORADA 2 ---
        Temporada pbT2 = new Temporada(2);
        pbT2.addCapitulo(new Capitulo(1, "Episodio 1", "Los Shelby planean expandir su imperio a Londres, pero una explosión en su pub local demuestra que tendrán competencia."));
        pbT2.addCapitulo(new Capitulo(2, "Episodio 2", "Tommy se alía con el líder mafioso judío Alfie Solomons, mientras los italianos de Sabini dan una paliza a Arthur."));
        pbT2.addCapitulo(new Capitulo(3, "Episodio 3", "Tommy contrata a nuevos reclutas y se reencuentra con un rostro conocido en una subasta de caballos."));
        pbT2.addCapitulo(new Capitulo(4, "Episodio 4", "El control de los Peaky en Londres se tambalea. Arthur sufre los estragos de la cocaína mientras Campbell presiona a Tommy."));
        pbT2.addCapitulo(new Capitulo(5, "Episodio 5", "Una traición inesperada de Alfie Solomons deja a los Shelby en una posición vulnerable frente a Sabini."));
        pbT2.addCapitulo(new Capitulo(6, "Episodio 6", "El día del Derby en Epsom llega con un intento de asesinato, y Tommy se enfrenta a su posible ejecución en un descampado."));
        peaky.addTemporada(pbT2);

        // --- TEMPORADA 3 ---
        Temporada pbT3 = new Temporada(3);
        pbT3.addCapitulo(new Capitulo(1, "Episodio 1", "El día de la boda de Tommy y Grace se ve interrumpido por la llegada de misteriosos refugiados rusos."));
        pbT3.addCapitulo(new Capitulo(2, "Episodio 2", "Un trágico suceso durante la cena de la Fundación Shelby hunde a Tommy en una espiral de dolor y sed de venganza."));
        pbT3.addCapitulo(new Capitulo(3, "Episodio 3", "Tommy viaja a Gales buscando respuestas. En casa, los Shelby sufren la amenaza de la familia Changretta."));
        pbT3.addCapitulo(new Capitulo(4, "Episodio 4", "Polly asiste al confesionario y revela demasiada información, poniendo en alerta al peligroso Padre Hughes."));
        pbT3.addCapitulo(new Capitulo(5, "Episodio 5", "A medida que los rusos se vuelven más inestables, Tommy planea un audaz robo de joyas con ayuda externa."));
        pbT3.addCapitulo(new Capitulo(6, "Episodio 6", "El secuestro del pequeño Charles obliga a Tommy a llevar a cabo una misión extrema que culmina en una inesperada traición a su propia familia."));
        peaky.addTemporada(pbT3);

        // --- TEMPORADA 4 ---
        Temporada pbT4 = new Temporada(4);
        pbT4.addCapitulo(new Capitulo(1, "La Mano Negra", "La llegada de Luca Changretta y la mafia de Nueva York desata una vendetta que cobra su primera víctima en la familia Shelby."));
        pbT4.addCapitulo(new Capitulo(2, "Salvajes", "Los Shelby se repliegan a Small Heath por seguridad y contratan a los peligrosos hombres de Aberama Gold como sicarios."));
        pbT4.addCapitulo(new Capitulo(3, "El mirlo", "Arthur es emboscado en la fábrica, demostrando que los italianos pueden atacar en cualquier lugar."));
        pbT4.addCapitulo(new Capitulo(4, "Peligro", "La familia tiende una trampa a los asesinos, mientras Polly se reúne en secreto con Luca Changretta."));
        pbT4.addCapitulo(new Capitulo(5, "El duelo", "Un tenso combate de boxeo sirve de tapadera para un enfrentamiento sangriento donde Arthur sufre un destino aparentemente fatal."));
        pbT4.addCapitulo(new Capitulo(6, "La compañía", "Tommy y Luca se enfrentan en un último cara a cara. Un regreso clave cambia las tornas y catapulta a Tommy a la política."));
        peaky.addTemporada(pbT4);

        // --- TEMPORADA 5 --- a falta
        // --- TEMPORADA 6 --- a falta

        // ==========================================
        // 3. PRISON BREAK (Categoría SILVER) - 4 Temporadas (Fiel a la serie)
        // ==========================================
        SerieSilver prison = new SerieSilver("S02", "Prison Break", "Michael Scofield elabora un plan magistral, tatuado en su cuerpo, para entrar en la prisión de Fox River y salvar a su hermano inocente del corredor de la muerte.", paulScheuring);
        prison.getActores().addAll(Arrays.asList(wentworth, dominic, amaury, robert, sarah));

        // --- TEMPORADA 1: Fox River ---
        Temporada prT1 = new Temporada(1);
        prT1.addCapitulo(new Capitulo(1, "Piloto", "Michael Scofield atraca un banco con el único objetivo de que lo encierren en Fox River junto a su hermano Lincoln."));
        prT1.addCapitulo(new Capitulo(2, "Allen", "Comienzan los preparativos de la fuga. Michael necesita un tornillo de las gradas, pero se topa con la resistencia de T-Bag."));
        prT1.addCapitulo(new Capitulo(3, "Cell Test", "Para avanzar con el plan, Michael pone a prueba la lealtad de su compañero de celda, Sucre, escondiendo un teléfono móvil."));
        prT1.addCapitulo(new Capitulo(4, "Cute Poison", "Lincoln recibe una advertencia letal. Mientras, Michael obtiene productos químicos para corroer las tuberías de la enfermería."));
        prT1.addCapitulo(new Capitulo(5, "English, Fitz or Percy", "Para descubrir qué rutas de escape estarán despejadas, Michael fuerza a los guardias a activar la alarma general."));
        prT1.addCapitulo(new Capitulo(6, "Riots, Drills and the Devil", "Michael sabotea el aire acondicionado para provocar un encierro en las celdas, pero la situación escala hasta un violento motín."));
        prison.addTemporada(prT1);

        // --- TEMPORADA 2: La Cacería ---
        Temporada prT2 = new Temporada(2);
        prT2.addCapitulo(new Capitulo(1, "Manhunt", "Ocho horas tras la fuga, el brillante agente del FBI Alexander Mahone toma el mando de la persecución de los fugitivos."));
        prT2.addCapitulo(new Capitulo(2, "Otis", "Mahone intenta usar a L.J. para atrapar a Lincoln, mientras Michael y los demás intentan mezclarse con la población civil."));
        prT2.addCapitulo(new Capitulo(3, "Scanlon", "Michael y Lincoln acuden a buscar ayuda médica para un fugitivo herido. Sucre y C-Note siguen su propio camino hacia Utah."));
        prT2.addCapitulo(new Capitulo(4, "First Down", "Bellick, sediento de venganza y recompensa, se asocia con un aliado inesperado para dar caza a los hermanos."));
        prT2.addCapitulo(new Capitulo(5, "Map 1213", "Varios fugitivos convergen en Tooele, Utah, desesperados por encontrar los cinco millones de dólares de Charles Westmoreland."));
        prT2.addCapitulo(new Capitulo(6, "Subdivision", "El grupo descubre que el silo donde está enterrado el dinero ahora es el garaje de una casa habitada."));
        prison.addTemporada(prT2);

        // --- TEMPORADA 3: Sona ---
        Temporada prT3 = new Temporada(3);
        prT3.addCapitulo(new Capitulo(1, "Orientación", "Atrapado en Sona, una infernal prisión panameña sin guardias, Michael debe sobrevivir a las mortales reglas del capo Lechero."));
        prT3.addCapitulo(new Capitulo(2, "Fire/Water", "La falta de agua desata el caos en Sona. Michael debe encontrar el origen del problema para evitar un motín sangriento."));
        prT3.addCapitulo(new Capitulo(3, "Call Waiting", "Michael necesita contactar desesperadamente con Lincoln y se ve obligado a pedirle un favor al peligroso T-Bag."));
        prT3.addCapitulo(new Capitulo(4, "Good Fences", "Lincoln planea un rescate exterior mientras Michael se asocia con el sepulturero de la prisión para asegurar una salida."));
        prT3.addCapitulo(new Capitulo(5, "Interference", "Un nuevo preso con un pasado turbio llega a Sona, complicando enormemente los planes de escape de Michael."));
        prT3.addCapitulo(new Capitulo(6, "Photo Finish", "Lincoln descubre un macabro secreto, pero se ve forzado a ocultárselo a Michael para que no abandone la misión."));
        prison.addTemporada(prT3);

        // --- TEMPORADA 4: Scylla ---
        Temporada prT4 = new Temporada(4);
        prT4.addCapitulo(new Capitulo(1, "Scylla", "El agente de Seguridad Nacional Don Self reúne a los hermanos para destruir a La Compañía recuperando el misterioso disco Scylla."));
        prT4.addCapitulo(new Capitulo(2, "Breaking and Entering", "El equipo descubre que Scylla no es un disco, sino seis. Comienza la carrera contrarreloj para copiar la primera tarjeta."));
        prT4.addCapitulo(new Capitulo(3, "Shut Down", "El dispositivo de copia falla y el equipo debe infiltrarse en un centro de servidores altamente vigilado."));
        prT4.addCapitulo(new Capitulo(4, "Eagles and Angels", "Lincoln, Mahone y Sara asisten encubiertos a un evento de la policía para acercarse al portador de la siguiente tarjeta."));
        prT4.addCapitulo(new Capitulo(5, "Safe and Sound", "Mahone debe confrontar a la persona responsable de la tragedia de su familia, mientras el equipo irrumpe en un banco."));
        prT4.addCapitulo(new Capitulo(6, "Blow Out", "Una operación en un hipódromo sale mal, dejando a uno de los miembros del equipo a merced de las autoridades."));
        prison.addTemporada(prT4);

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