package co.com.bancolombia.api.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.cors.reactive.CorsWebFilter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Pruebas de integración para la configuración de CORS en {@link CorsConfig}.
 *
 * <p>Verifica que el bean CorsWebFilter se cree correctamente en el contexto de Spring.</p>
 */
@SpringBootTest(classes = CorsConfig.class)
@TestPropertySource(properties = "cors.allowed-origins=http://localhost")
class CorsConfigTest {

    @Autowired
    private CorsWebFilter corsWebFilter;

    /**
     * Verifica que el bean CorsWebFilter esté presente en el contexto de Spring.
     */
    @Test
    void corsWebFilterBeanShouldBeCreated() {
        assertNotNull(corsWebFilter, "El bean CorsWebFilter debe estar presente en el contexto");
    }
} 