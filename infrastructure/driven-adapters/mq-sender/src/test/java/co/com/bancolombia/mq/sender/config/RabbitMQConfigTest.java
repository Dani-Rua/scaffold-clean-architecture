package co.com.bancolombia.mq.sender.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

/**
 * Pruebas de integración para la configuración de RabbitMQ en {@link RabbitMQConfig}.
 *
 * <p>Verifica la correcta creación y configuración de los beans de RabbitMQ en el contexto de Spring.</p>
 */
@SpringBootTest(classes = RabbitMQConfig.class)
@Import(RabbitMQConfig.class)
class RabbitMQConfigTest {

    @Autowired
    private MessageConverter messageConverter;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private ConnectionFactory connectionFactory;

    /**
     * Verifica que el bean MessageConverter se cree y sea de tipo Jackson2JsonMessageConverter.
     */
    @Test
    void jsonMessageConverterBeanShouldBeCreated() {
        assertNotNull(messageConverter);
        assertTrue(messageConverter instanceof Jackson2JsonMessageConverter);
    }

    /**
     * Verifica que el bean RabbitTemplate se cree y utilice el MessageConverter JSON.
     */
    @Test
    void rabbitTemplateBeanShouldBeCreated() {
        assertNotNull(rabbitTemplate);
        assertNotNull(rabbitTemplate.getMessageConverter());
        assertTrue(rabbitTemplate.getMessageConverter() instanceof Jackson2JsonMessageConverter);
    }
}
