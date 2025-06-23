package co.com.bancolombia.model.stats.gateways;

import co.com.bancolombia.model.stats.Stats;
import reactor.core.publisher.Mono;


/**
 * Interfaz gateway para la persistencia y consulta de entidades {@link Stats}.
 *
 * <p>Define el contrato para operaciones de almacenamiento y recuperación de estadísticas en la capa de dominio.</p>
 */
public interface StatsRepository {
    /**
     * Guarda una entidad de estadísticas en el repositorio.
     *
     * @param stats Entidad de estadísticas a guardar
     * @return Mono que emite la entidad guardada o un error si falla
     */
    Mono<Stats> saveStats(Stats stats);

    /**
     * Busca una entidad de estadísticas por su clave (timestamp).
     *
     * @param timestamp Clave de búsqueda (puede ser un string representando la fecha/hora)
     * @return Mono que emite la entidad encontrada o vacío si no existe
     */
}
