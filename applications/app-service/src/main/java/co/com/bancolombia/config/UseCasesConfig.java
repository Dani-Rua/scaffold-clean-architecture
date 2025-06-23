package co.com.bancolombia.config;

import co.com.bancolombia.model.stats.gateways.EventPublisherGateway;
import co.com.bancolombia.model.stats.gateways.StatsRepository;
import co.com.bancolombia.usecase.stats.StatsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * Configuración de casos de uso de la aplicación.
 *
 * <p>Esta clase define cómo crear los beans de casos de uso que no pueden
 * ser detectados automáticamente por Spring debido a restricciones de
 * Clean Architecture.</p>
 */
@Configuration
@ComponentScan(basePackages = "co.com.bancolombia.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {

        /**
         * Crea y configura el caso de uso para estadísticas.
         *
         * <p>Este bean construye manualmente el StatsUseCase e inyecta sus
         * dependencias requeridas: el repositorio de estadísticas y el
         * publicador de eventos.</p>
         *
         * @param statsRepository Repositorio para operaciones de base de datos
         * @param eventPublisherGateway Gateway para publicar eventos en RabbitMQ
         * @return Instancia configurada de StatsUseCase
         */
        @Bean
        public StatsUseCase statsUseCase(StatsRepository statsRepository, EventPublisherGateway eventPublisherGateway) {
                return new StatsUseCase(statsRepository, eventPublisherGateway);
        }
}
