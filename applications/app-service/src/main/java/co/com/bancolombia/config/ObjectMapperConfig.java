package co.com.bancolombia.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.reactivecommons.utils.ObjectMapperImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Configuración de beans de ObjectMapper para la serialización y deserialización de JSON.
 *
 * <p>Define dos beans:
 * <ul>
 *   <li>Un {@link ObjectMapper} de Jackson personalizado para Spring y la aplicación.</li>
 *   <li>Un {@link org.reactivecommons.utils.ObjectMapper} para Reactive Commons.</li>
 * </ul>
 */
@Configuration
public class ObjectMapperConfig {

    /**
     * Crea un bean principal de {@link ObjectMapper} configurado para manejar fechas y propiedades desconocidas.
     *
     * @return una instancia personalizada de ObjectMapper
     */
    @Bean
    @Primary
    public ObjectMapper jacksonObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    /**
     * Crea un bean de {@link org.reactivecommons.utils.ObjectMapper} para Reactive Commons.
     *
     * @return una instancia de ObjectMapperImp
     */
    @Bean
    public org.reactivecommons.utils.ObjectMapper reactiveCommonsObjectMapper() {
        return new ObjectMapperImp();
    }
}
