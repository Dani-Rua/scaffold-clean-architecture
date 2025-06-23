package co.com.bancolombia.api.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.mock.web.server.MockServerWebExchange;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para {@link GlobalExceptionHandler}.
 *
 * <p>Verifica el manejo centralizado de excepciones en la API, asegurando respuestas HTTP consistentes para errores de validación y errores internos.</p>
 */
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    /**
     * Verifica que una IllegalArgumentException sea manejada como un error 400 Bad Request con el mensaje adecuado.
     */
    @Test
    void handleIllegalArgumentException_ReturnsBadRequest() {
        // Arrange
        String errorMsg = "Mensaje de error de validación";
        IllegalArgumentException ex = new IllegalArgumentException(errorMsg);
        ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/test").build());

        // Act
        Mono<Void> result = exceptionHandler.handle(exchange, ex);
        result.block();

        // Assert
        MockServerHttpResponse response = (MockServerHttpResponse) exchange.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        String body = response.getBodyAsString().block();
        assertTrue(body.contains(errorMsg));
    }

    /**
     * Verifica que una excepción genérica sea manejada como un error 500 Internal Server Error con un mensaje genérico.
     */
    @Test
    void handleGenericException_ReturnsInternalServerError() {
        // Arrange
        RuntimeException ex = new RuntimeException("Error inesperado");
        ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/test").build());

        // Act
        Mono<Void> result = exceptionHandler.handle(exchange, ex);
        result.block();

        // Assert
        MockServerHttpResponse response = (MockServerHttpResponse) exchange.getResponse();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        String body = response.getBodyAsString().block();
        assertTrue(body.contains("Error interno del servidor"));
    }
}
