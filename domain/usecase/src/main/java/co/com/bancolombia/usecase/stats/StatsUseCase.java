package co.com.bancolombia.usecase.stats;

import co.com.bancolombia.model.stats.Stats;
import co.com.bancolombia.model.stats.gateways.EventPublisherGateway;
import co.com.bancolombia.model.stats.gateways.StatsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@RequiredArgsConstructor
public class StatsUseCase {

    private final StatsRepository statsRepository;
    private final EventPublisherGateway eventPublisher;

    public Mono<Stats> saveStats(Stats stats) {
        if (!isValidHash(stats)) {
            return Mono.error(new IllegalArgumentException("Hash MD5 inválido"));
        }

        stats.setTimestamp();
        return statsRepository.saveStats(stats)
                .flatMap(savedStats -> eventPublisher.publishEvent(savedStats)
                        .thenReturn(savedStats));
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
        // Concatenar los campos en el orden especificado: 250,25,10,100,100,7,8
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

    public Mono<Stats> findByKey(String timestamp) {
        return statsRepository.findByKey(timestamp);
    }
}
