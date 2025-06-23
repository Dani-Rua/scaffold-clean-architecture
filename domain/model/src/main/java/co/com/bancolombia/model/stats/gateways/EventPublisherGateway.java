package co.com.bancolombia.model.stats.gateways;


import co.com.bancolombia.model.stats.Stats;
import reactor.core.publisher.Mono;

/**
 * Define el contrato para publicar eventos en un sistema de mensajería.
 * Esta interfaz abstrae la tecnología subyacente (ej. RabbitMQ, SQS, etc.).
 */
public interface EventPublisherGateway {

    /**
     * Publica un evento relacionado con una estadística.
     *
     * @param stats El objeto de estadísticas que se publicará como evento.
     * @return Un Mono<Void> que se completa cuando la publicación es exitosa,
     * o emite un error si falla.
     */
    Mono<Void> publishEvent(Stats stats);
}
