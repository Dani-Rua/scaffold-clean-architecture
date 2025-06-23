package co.com.bancolombia.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import co.com.bancolombia.usecase.stats.StatsUseCase;
import co.com.bancolombia.api.validator.RequestValidator;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import co.com.bancolombia.api.dto.StatsDTO;
import co.com.bancolombia.model.stats.Stats;
import reactor.core.publisher.Mono;

/**
 * Pruebas de integración para el ruteo y manejo del endpoint /api/v1/stats.
 *
 * <p>Verifica que el endpoint POST reciba correctamente el body, pase por el flujo de validación y caso de uso,
 * y retorne la respuesta esperada en formato JSON.</p>
 */
@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@WebFluxTest
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private StatsUseCase statsUseCase;

    @MockBean
    private RequestValidator requestValidator;

    /**
     * Verifica que el endpoint POST /api/v1/stats procese correctamente una petición válida y retorne los datos esperados.
     */
    @Test
    void testListenPOSTStats() {
        // Construir un body válido según StatsDTO
        String body = """
        {
            \"totalContactoClientes\": 250,
            \"motivoReclamo\": 25,
            \"motivoGarantia\": 10,
            \"motivoDuda\": 100,
            \"motivoCompra\": 100,
            \"motivoFelicitaciones\": 7,
            \"motivoCambio\": 8,
            \"hash\": \"5484062a4be1ce5645eb414663e14f59\"
        }
        """;

        // Configurar mocks para el flujo
        when(requestValidator.validate(any(StatsDTO.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        when(statsUseCase.saveStats(any(Stats.class)))
                .thenAnswer(invocation -> {
                    Stats stats = invocation.getArgument(0);
                    return Mono.just(stats);
                });

        webTestClient.post()
                .uri("/api/v1/stats")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.totalContactoClientes").isEqualTo(250)
                .jsonPath("$.motivoReclamo").isEqualTo(25)
                .jsonPath("$.motivoGarantia").isEqualTo(10)
                .jsonPath("$.motivoDuda").isEqualTo(100)
                .jsonPath("$.motivoCompra").isEqualTo(100)
                .jsonPath("$.motivoFelicitaciones").isEqualTo(7)
                .jsonPath("$.motivoCambio").isEqualTo(8)
                .jsonPath("$.hash").isEqualTo("5484062a4be1ce5645eb414663e14f59");
    }
}
