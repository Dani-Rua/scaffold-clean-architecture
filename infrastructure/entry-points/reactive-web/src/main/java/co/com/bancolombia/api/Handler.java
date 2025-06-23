package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.StatsDTO;
import co.com.bancolombia.api.mapper.StatsMapper;
import co.com.bancolombia.api.validator.RequestValidator;
import co.com.bancolombia.model.stats.Stats;
import co.com.bancolombia.usecase.stats.StatsUseCase;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;



/**
 * Handler para las peticiones HTTP relacionadas con las estadísticas.
 *
 * <p>Esta clase se encarga de recibir las peticiones, delegar la lógica de negocio
 * al caso de uso correspondiente y manejar la construcción de la respuesta HTTP.</p>
 */
@Component
@RequiredArgsConstructor

public class Handler {

    private final StatsUseCase statsUseCase;
    private final RequestValidator requestValidator;

    /**
     * Maneja las peticiones POST al endpoint /stats.
     *
     * <p>Este método sigue los siguientes pasos:</p>
     * <ol>
     *   <li>Recibe el cuerpo de la petición y lo convierte a un {@link StatsDTO}.</li>
     *   <li>Utiliza {@link StatsMapper} para convertir el DTO a la entidad de dominio {@link Stats}.</li>
     *   <li>Delega el procesamiento de la estadística al {@link StatsUseCase}.</li>
     *   <li>Construye una respuesta HTTP 200 OK con la estadística guardada si el proceso es exitoso.</li>
     * </ol>
     *
     * <p>Cualquier error durante el proceso (ej. hash inválido) es capturado por
     * el manejador de excepciones global.</p>
     *
     * @param serverRequest La petición HTTP entrante.
     * @return Un {@link Mono} que emite la respuesta del servidor.
     */

    public Mono<ServerResponse> listenPOSTStats(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(StatsDTO.class)
                .flatMap(requestValidator::validate)
                .map(StatsMapper.INSTANCE::toStats)
                .flatMap(statsUseCase::saveStats)
                .flatMap(savedStats -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(StatsMapper.INSTANCE.toStatsDTO(savedStats))
                );
    }
}
