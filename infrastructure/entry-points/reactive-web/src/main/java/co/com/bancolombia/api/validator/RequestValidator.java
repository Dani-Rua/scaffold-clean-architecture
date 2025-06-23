package co.com.bancolombia.api.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Componente encargado de validar los objetos DTO de entrada.
 *
 * <p>Esta clase centraliza la lógica de validación utilizando el
 * {@link Validator} de Jakarta Bean Validation para asegurar que los
 * datos de entrada cumplan con las restricciones definidas en los DTOs.</p>
 */
@Component
@RequiredArgsConstructor
public class RequestValidator {

    private final Validator validator;

    /**
     * Valida un objeto DTO.
     *
     * <p>Si el objeto es válido, lo emite a través de un {@link Mono}.
     * Si se encuentran violaciones de las restricciones, emite un
     * {@link Mono.error} con una {@link IllegalArgumentException} que
     * contiene los mensajes de error concatenados.</p>
     *
     * @param dto El objeto DTO a validar.
     * @param <T> El tipo del objeto DTO.
     * @return Un {@link Mono} que emite el DTO si es válido, o un error si no lo es.
     */
    public <T> Mono<T> validate(T dto) {
        if (dto == null) {
            return Mono.error(new IllegalArgumentException("El cuerpo de la petición no puede ser nulo"));
        }

        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            String errors = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
            return Mono.error(new IllegalArgumentException(errors));
        }

        return Mono.just(dto);
    }
} 