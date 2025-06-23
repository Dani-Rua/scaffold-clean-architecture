package co.com.bancolombia.api.mapper;


import co.com.bancolombia.api.dto.StatsDTO;
import co.com.bancolombia.model.stats.Stats;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Interfaz de mapeo entre StatsDTO y Stats.
 *
 * <p>Esta interfaz utiliza MapStruct para generar automáticamente la implementación
 * de los métodos de conversión entre el DTO y la entidad de dominio.</p>
 */
@Mapper
public interface StatsMapper {
    StatsMapper INSTANCE = Mappers.getMapper(StatsMapper.class);

    /**
     * Convierte un StatsDTO a una entidad Stats.
     *
     * @param dto El DTO a convertir
     * @return La entidad Stats convertida
     */
    Stats toStats(StatsDTO dto);

    /**
     * Convierte una entidad Stats a un StatsDTO.
     *
     * @param stats La entidad a convertir
     * @return El StatsDTO convertido
     */
    StatsDTO toStatsDTO(Stats stats);
}
