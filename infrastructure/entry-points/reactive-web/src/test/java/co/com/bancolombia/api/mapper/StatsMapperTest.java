package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.StatsDTO;
import co.com.bancolombia.model.stats.Stats;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Pruebas unitarias para {@link StatsMapper}.
 *
 * <p>Verifica la conversión entre el DTO {@link StatsDTO} y la entidad de dominio {@link Stats} usando MapStruct.</p>
 */
class StatsMapperTest {

    /**
     * Instancia de la implementación generada por MapStruct para StatsMapper.
     */
    private final StatsMapper statsMapper = Mappers.getMapper(StatsMapper.class);

    /**
     * Verifica que el mapeo de StatsDTO a Stats se realice correctamente.
     *
     * <ul>
     *   <li>Crea un objeto DTO de prueba.</li>
     *   <li>Llama al método de mapeo.</li>
     *   <li>Verifica que el mapeo fue correcto comparando los campos.</li>
     * </ul>
     */
    @Test
    void shouldMapDtoToDomain() {
        StatsDTO dto = new StatsDTO();
        dto.setTotalContactoClientes(250);
        dto.setMotivoReclamo(25);
        dto.setMotivoGarantia(10);
        dto.setMotivoDuda(100);
        dto.setMotivoCompra(100);
        dto.setMotivoFelicitaciones(7);
        dto.setMotivoCambio(8);
        dto.setHash("some-hash");

        Stats domain = statsMapper.toStats(dto);

        assertNotNull(domain);
        assertEquals(dto.getTotalContactoClientes(), domain.getTotalContactoClientes());
        assertEquals(dto.getMotivoReclamo(), domain.getMotivoReclamo());
        assertEquals(dto.getMotivoGarantia(), domain.getMotivoGarantia());
        assertEquals(dto.getMotivoDuda(), domain.getMotivoDuda());
        assertEquals(dto.getMotivoCompra(), domain.getMotivoCompra());
        assertEquals(dto.getMotivoFelicitaciones(), domain.getMotivoFelicitaciones());
        assertEquals(dto.getMotivoCambio(), domain.getMotivoCambio());
        assertEquals(dto.getHash(), domain.getHash());
    }

    /**
     * Verifica que el mapeo de Stats a StatsDTO se realice correctamente.
     *
     * <ul>
     *   <li>Crea un objeto de dominio de prueba.</li>
     *   <li>Llama al método de mapeo.</li>
     *   <li>Verifica que el mapeo fue correcto comparando los campos.</li>
     * </ul>
     */
    @Test
    void shouldMapDomainToDto() {
        Stats domain = new Stats();
        domain.setTotalContactoClientes(250);
        domain.setMotivoReclamo(25);
        domain.setMotivoGarantia(10);
        domain.setMotivoDuda(100);
        domain.setMotivoCompra(100);
        domain.setMotivoFelicitaciones(7);
        domain.setMotivoCambio(8);
        domain.setHash("some-hash");
        domain.setTimestamp();

        StatsDTO dto = statsMapper.toStatsDTO(domain);

        assertNotNull(dto);
        assertEquals(domain.getTotalContactoClientes(), dto.getTotalContactoClientes());
        assertEquals(domain.getMotivoReclamo(), dto.getMotivoReclamo());
        assertEquals(domain.getMotivoGarantia(), dto.getMotivoGarantia());
        assertEquals(domain.getMotivoDuda(), dto.getMotivoDuda());
        assertEquals(domain.getMotivoCompra(), dto.getMotivoCompra());
        assertEquals(domain.getMotivoFelicitaciones(), dto.getMotivoFelicitaciones());
        assertEquals(domain.getMotivoCambio(), dto.getMotivoCambio());
        assertEquals(domain.getHash(), dto.getHash());
    }
}
