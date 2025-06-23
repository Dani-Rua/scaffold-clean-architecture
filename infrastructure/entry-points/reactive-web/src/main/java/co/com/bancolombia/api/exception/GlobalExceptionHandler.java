package co.com.bancolombia.api.exception;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * Manejador de excepciones global para la aplicación reactiva.
 *
 * <p>Esta clase implementa {@link ErrorWebExceptionHandler} para interceptar todas
 * las excepciones no controladas que ocurren en el flujo reactivo. Le da un
 * tratamiento centralizado a los errores y asegura que las respuestas HTTP
 * tengan un formato consistente.</p>
 *
 * <p>Gracias a la anotación {@code @Order(-2)}, este manejador tiene prioridad
 * sobre el manejador de errores por defecto de Spring Boot.</p>
 */
@Component
@Order(-2)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    /**
     * Método principal que maneja las excepciones.
     *
     * <p>Este método recibe la excepción y el contexto del intercambio HTTP.
     * Determina el tipo de excepción y delega a un método específico para
     * construir la respuesta HTTP apropiada.</p>
     *
     * @param exchange El contexto del intercambio HTTP actual.
     * @param ex La excepción que fue lanzada.
     * @return Un {@link Mono<Void>} que indica que la respuesta ha sido manejada.
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof IllegalArgumentException) {
            return handleIllegalArgumentException(exchange, (IllegalArgumentException) ex);
        }

        // Fallback para cualquier otra excepción no esperada
        return handleGenericException(exchange, ex);
    }

    /**
     * Maneja las excepciones de tipo {@link IllegalArgumentException}.
     *
     * <p>Este método es invocado cuando se lanza una excepción de argumento ilegal,
     * típicamente usada para errores de validación (ej. hash inválido). Construye
     * una respuesta HTTP 400 Bad Request con un mensaje de error claro.</p>
     *
     * @param exchange El contexto del intercambio HTTP.
     * @param ex La excepción específica de argumento ilegal.
     * @return Un {@link Mono<Void>} que completa la respuesta.
     */
    private Mono<Void> handleIllegalArgumentException(ServerWebExchange exchange, IllegalArgumentException ex) {
        exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String responseBody = "{\"error\": \"" + ex.getMessage() + "\"}";

        return exchange.getResponse().writeWith(
            Mono.just(exchange.getResponse().bufferFactory().wrap(responseBody.getBytes(StandardCharsets.UTF_8)))
        );
    }

    /**
     * Maneja excepciones genéricas no esperadas.
     *
     * <p>Este es el manejador de último recurso. Atrapa cualquier excepción que no
     * haya sido manejada por los métodos específicos y retorna una respuesta
     * HTTP 500 Internal Server Error con un mensaje genérico para no exponer
     * detalles de la implementación.</p>
     *
     * @param exchange El contexto del intercambio HTTP.
     * @param ex La excepción genérica.
     * @return Un {@link Mono<Void>} que completa la respuesta.
     */
    private Mono<Void> handleGenericException(ServerWebExchange exchange, Throwable ex) {
        // Imprimir el stack trace para depuración en la consola del servidor
        ex.printStackTrace();

        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String responseBody = "{\"error\": \"Error interno del servidor\"}";

        return exchange.getResponse().writeWith(
            Mono.just(exchange.getResponse().bufferFactory().wrap(responseBody.getBytes(StandardCharsets.UTF_8)))
        );
    }
} 