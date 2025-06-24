package co.com.bancolombia.usecase.stats;

import co.com.bancolombia.model.stats.Stats;
import co.com.bancolombia.model.stats.gateways.EventPublisherGateway;
import co.com.bancolombia.model.stats.gateways.StatsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Caso de uso principal para el procesamiento de estadísticas de interacción de clientes.
 *
 * <p>Valida el hash MD5, asigna el timestamp, guarda la estadística en DynamoDB y publica el evento en RabbitMQ.</p>
 */
@RequiredArgsConstructor
public class StatsUseCase {

    private final StatsRepository statsRepository;
    private final EventPublisherGateway eventPublisher;

    /**
     * Procesa y guarda una estadística si el hash es válido.
     *
     * <p>Valida el hash MD5, asigna el timestamp actual, guarda la estadística y publica el evento.</p>
     *
     * @param stats La estadística a procesar
     * @return Mono que emite la estadística guardada o un error si el hash es inválido
     */
    public Mono<Stats> saveStats(Stats stats) {
        return Mono.just(stats)
                .filter(this::isValidHash)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Hash MD5 inválido")))
                .map(s -> {
                    s.setTimestamp();
                    return s;
                })
                .flatMap(statsRepository::saveStats)
                .flatMap(savedStats -> eventPublisher.publishEvent(savedStats).thenReturn(savedStats));
    }

    /**
     * Valida que el hash MD5 sea correcto.
     * @param stats La estadística a validar
     * @return true si el hash es válido, false en caso contrario
     */
    private boolean isValidHash(Stats stats) {
        try {
            String expectedHash = generateMD5Hash(stats);
            return expectedHash.equals(stats.getHash());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Genera el hash MD5 esperado con los datos de la estadística.
     * @param stats La estadística para generar el hash
     * @return El hash MD5 generado
     * @throws NoSuchAlgorithmException si el algoritmo MD5 no está disponible
     */
    private String generateMD5Hash(Stats stats) throws NoSuchAlgorithmException {
        String data = String.format("%d,%d,%d,%d,%d,%d,%d",
                stats.getTotalContactoClientes(),
                stats.getMotivoReclamo(),
                stats.getMotivoGarantia(),
                stats.getMotivoDuda(),
                stats.getMotivoCompra(),
                stats.getMotivoFelicitaciones(),
                stats.getMotivoCambio());

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md.digest(data.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
