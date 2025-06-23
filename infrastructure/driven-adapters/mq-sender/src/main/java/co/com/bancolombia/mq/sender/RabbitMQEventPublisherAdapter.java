package co.com.bancolombia.mq.sender;

import co.com.bancolombia.model.stats.Stats;
import co.com.bancolombia.model.stats.gateways.EventPublisherGateway;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Adaptador para publicar eventos de estadísticas en RabbitMQ.
 * Implementa EventPublisherGateway usando Spring AMQP.
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQEventPublisherAdapter implements EventPublisherGateway/* implements SomeGateway */ {

    /**
     * Template de RabbitMQ para enviar mensajes.
     */
    private final RabbitTemplate rabbitTemplate;

    /**
     * Nombre del exchange donde se publican los eventos de estadísticas.
     */
    @Value("${app.rabbitmq.exchange.stats}")
    private String statsExchange;

    /**
     * Clave de enrutamiento para los eventos de estadísticas.
     */
    @Value("${app.rabbitmq.routing-key.stats}")
    private String statsRoutingKey;

    /**
     * Crea el exchange de RabbitMQ si no existe.
     * Se ejecuta después de la construcción del bean.
     */
    @PostConstruct
    public void createExchangeIfNotExists() {
        try {
            rabbitTemplate.execute(channel -> {
                channel.exchangeDeclare(statsExchange, "direct", true);
                return null;
            });
            log.info("Exchange '{}' creado o verificado exitosamente", statsExchange);
        } catch (Exception e) {
            log.error("Error creando exchange '{}': {}", statsExchange, e.getMessage());
        }
    }

    /**
     * Publica un evento de estadísticas en RabbitMQ.
     *
     * @param stats El objeto de estadísticas a publicar
     * @return Mono<Void> que se completa cuando la publicación es exitosa
     */
    @Override
    public Mono<Void> publishEvent(Stats stats) {
        log.info("Publicando evento de estadísticas en RabbitMQ. Exchange: {}, Routing Key: {}", statsExchange, statsRoutingKey);

        return Mono.fromCallable(() -> {
            rabbitTemplate.convertAndSend(statsExchange, statsRoutingKey, stats);
            return null;
        }).then();
    }
}
