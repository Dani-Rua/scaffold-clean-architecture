package co.com.bancolombia.mq.sender.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de RabbitMQ para el módulo mq-sender.
 *
 * <p>Esta clase configura el message converter JSON para RabbitMQ,
 * permitiendo la serialización/deserialización automática de objetos
 * Java a JSON y viceversa.</p>
 */
@Configuration
public class RabbitMQConfig {

    /**
     * Configura el message converter JSON para RabbitMQ.
     *
     * <p>Este bean permite que RabbitMQ serialice automáticamente
     * objetos Java a JSON y viceversa, resolviendo el problema
     * de serialización con objetos complejos como Stats.</p>
     *
     * @return Jackson2JsonMessageConverter configurado
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Configura el RabbitTemplate con el message converter JSON.
     *
     * <p>Este bean personaliza el RabbitTemplate para usar el
     * message converter JSON en lugar del SimpleMessageConverter
     * por defecto.</p>
     *
     * @param connectionFactory Factory de conexión de RabbitMQ
     * @param jsonMessageConverter Message converter JSON
     * @return RabbitTemplate configurado
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }
}
