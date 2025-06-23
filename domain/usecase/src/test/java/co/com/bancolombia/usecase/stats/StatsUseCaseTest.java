package co.com.bancolombia.usecase.stats;

import co.com.bancolombia.model.stats.Stats;
import co.com.bancolombia.model.stats.gateways.EventPublisherGateway;
import co.com.bancolombia.model.stats.gateways.StatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias para el caso de uso {@link StatsUseCase}.
 *
 * <p>Verifica el flujo de validación, guardado y publicación de eventos para las estadísticas de clientes.</p>
 */
@ExtendWith(MockitoExtension.class)
class StatsUseCaseTest {

    @Mock
    private StatsRepository statsRepository;

    @Mock
    private EventPublisherGateway eventPublisherGateway;

    @InjectMocks
    private StatsUseCase statsUseCase;

    private Stats validStats;
    private Stats invalidStats;

    /**
     * Configura los datos de prueba antes de cada test.
     * Crea una estadística válida y una inválida (por hash).
     */
    @BeforeEach
    void setUp() {

        validStats = new Stats();
        validStats.setTotalContactoClientes(250);
        validStats.setMotivoReclamo(25);
        validStats.setMotivoGarantia(10);
        validStats.setMotivoDuda(100);
        validStats.setMotivoCompra(100);
        validStats.setMotivoFelicitaciones(7);
        validStats.setMotivoCambio(8);
        validStats.setHash("5484062a4be1ce5645eb414663e14f59");

        invalidStats = new Stats();
        invalidStats.setTotalContactoClientes(250);
        invalidStats.setMotivoReclamo(25);
        invalidStats.setMotivoGarantia(10);
        invalidStats.setMotivoDuda(100);
        invalidStats.setMotivoCompra(100);
        invalidStats.setMotivoFelicitaciones(7);
        invalidStats.setMotivoCambio(8);
        invalidStats.setHash("hash_invalido");
    }

    /**
     * Verifica que se guarde la estadística y se publique el evento cuando el hash es válido.
     */
    @Test
    void shouldSaveStatsWhenHashIsValid() {

        when(statsRepository.saveStats(any(Stats.class)))
            .thenReturn(Mono.just(validStats));
        when(eventPublisherGateway.publishEvent(any(Stats.class)))
            .thenReturn(Mono.empty());


        Mono<Stats> result = statsUseCase.saveStats(validStats);


        StepVerifier.create(result)
            .expectNext(validStats)
            .verifyComplete();
    }

    /**
     * Verifica que se retorne un error cuando el hash es inválido.
     */
    @Test
    void shouldReturnErrorWhenHashIsInvalid() {

        Mono<Stats> result = statsUseCase.saveStats(invalidStats);


        StepVerifier.create(result)
            .expectError(IllegalArgumentException.class)
            .verify();
    }

    /**
     * Verifica que se retorne un error si el repositorio falla al guardar la estadística.
     */
    @Test
    void shouldReturnErrorWhenRepositoryFails() {

        when(statsRepository.saveStats(any(Stats.class)))
            .thenReturn(Mono.error(new RuntimeException("Database error")));


        Mono<Stats> result = statsUseCase.saveStats(validStats);


        StepVerifier.create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    /**
     * Verifica que se retorne un error si el publicador de eventos falla.
     */
    @Test
    void shouldReturnErrorWhenEventPublisherFails() {

        when(statsRepository.saveStats(any(Stats.class)))
            .thenReturn(Mono.just(validStats));
        when(eventPublisherGateway.publishEvent(any(Stats.class)))
            .thenReturn(Mono.error(new RuntimeException("RabbitMQ error")));


        Mono<Stats> result = statsUseCase.saveStats(validStats);


        StepVerifier.create(result)
            .expectError(RuntimeException.class)
            .verify();
    }
}
