package ec.edu.ups.clienteservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Permite solicitudes de cualquier origen
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Personaliza según tus necesidades
                .allowedHeaders("*") // Permite cualquier encabezado
                .maxAge(3600); // Tiempo en segundos que el navegador debe cachear los resultados de la verificación CORS
    }
}