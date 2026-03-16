package es.unican.carlosalarcon.polaflix;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class H2NativeConsoleConfig {

    // Levantamos un servidor web exclusivo para H2 en el puerto 8082
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2WebConsole() throws SQLException {
        System.out.println("=======================================================");
        System.out.println("🚀 ARRANCANDO CONSOLA H2 NATIVA EN: http://localhost:8082");
        System.out.println("=======================================================");
        
        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082");
    }
}