package co.com.bancolombia.mq.sender;

import co.com.bancolombia.model.stats.Stats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Pruebas unitarias para {@link RabbitMQEventPublisherAdapter}.
 *
 * <p>Verifica que el adaptador publique correctamente eventos en RabbitMQ usando el template de Spring.</p>
 */
class RabbitMQEventPublisherAdapterTest {

    private RabbitTemplate rabbitTemplate;
    private RabbitMQEventPublisherAdapter adapter;

    @BeforeEach
    void setUp() {
        rabbitTemplate = mock(RabbitTemplate.class);
        adapter = new RabbitMQEventPublisherAdapter(rabbitTemplate);
        // Usar reflexión para setear los campos privados
        ReflectionTestUtils.setField(adapter, "statsExchange", "test-exchange");
        ReflectionTestUtils.setField(adapter, "statsRoutingKey", "test-routing-key");
    }

    /**
     * Verifica que al invocar publishEvent se llame a convertAndSend con los parámetros correctos.
     */
    @Test
    void publishEvent_ShouldCallConvertAndSend() {
        // Arrange
        Stats stats = new Stats();

        // Act
        Mono<Void> result = adapter.publishEvent(stats);
        result.block();

        // Assert
        verify(rabbitTemplate, times(1)).convertAndSend("test-exchange", "test-routing-key", stats);
    }
} 