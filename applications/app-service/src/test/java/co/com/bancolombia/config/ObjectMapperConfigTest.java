package co.com.bancolombia.config;

import org.junit.jupiter.api.Test;
import org.reactivecommons.utils.ObjectMapper;
import org.reactivecommons.utils.ObjectMapperImp;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias para la clase {@link ObjectMapperConfig}.
 *
 * <p>Verifica que el bean de {@link ObjectMapper} se registre correctamente y sea una instancia de {@link ObjectMapperImp}.</p>
 */
class ObjectMapperConfigTest {

    /**
     * Verifica que el bean de ObjectMapper esté presente en el contexto y sea del tipo esperado.
     */
    @Test
    void testObjectMapperBean() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ObjectMapperConfig.class);
        ObjectMapper objectMapper = context.getBean(ObjectMapper.class);
        assertNotNull(objectMapper);
        assertTrue(objectMapper instanceof ObjectMapperImp);
        context.close();
    }
}