package es.unican.carlosalarcon.polaflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PolaflixApplication {

    public static void main(String[] args) {
        // Obligamos al sistema a activar la consola H2 saltándonos los archivos de configuración
        
        SpringApplication.run(PolaflixApplication.class, args);
    }
}