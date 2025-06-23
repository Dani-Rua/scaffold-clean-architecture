package co.com.bancolombia.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.mockito.Mockito;
import co.com.bancolombia.model.stats.gateways.StatsRepository;
import co.com.bancolombia.model.stats.gateways.EventPublisherGateway;

/**
 * Pruebas unitarias para la clase {@link UseCasesConfig}.
 *
 * <p>Verifica que los beans de casos de uso se registren correctamente en el contexto de Spring
 * cuando se proveen las dependencias necesarias mediante mocks.</p>
 */
public class UseCasesConfigTest {

    /**
     * Verifica que exista al menos un bean cuyo nombre termine en 'UseCase' en el contexto,
     * lo que indica que la configuración de casos de uso es exitosa.
     */
    @Test
    void testUseCaseBeansExist() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.registerBean(StatsRepository.class, () -> Mockito.mock(StatsRepository.class));
            context.registerBean(EventPublisherGateway.class, () -> Mockito.mock(EventPublisherGateway.class));
            context.register(UseCasesConfig.class);
            context.refresh();

            String[] beanNames = context.getBeanDefinitionNames();
            boolean useCaseBeanFound = false;
            for (String beanName : beanNames) {
                if (beanName.endsWith("UseCase")) {
                    useCaseBeanFound = true;
                    break;
                }
            }
            assertTrue(useCaseBeanFound, "No beans ending with 'UseCase' were found");
        }
    }
}