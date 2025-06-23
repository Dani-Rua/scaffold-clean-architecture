package co.com.bancolombia.api.validator;

import co.com.bancolombia.api.dto.StatsDTO;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Pruebas unitarias para {@link RequestValidator}.
 *
 * <p>Verifica la validación de DTOs de entrada, cubriendo casos válidos e inválidos.</p>
 */
class RequestValidatorTest {

    private RequestValidator requestValidator;
    private StatsDTO validDto;
    private StatsDTO invalidDto;

    /**
     * Inicializa el validador y los DTOs de prueba antes de cada test.
     *
     * <ul>
     *   <li>Crea una fábrica de validadores estándar de Jakarta.</li>
     *   <li>Obtiene una instancia del validador.</li>
     *   <li>Pasa el validador al constructor de RequestValidator.</li>
     *   <li>Configura un DTO válido con un hash en el formato correcto.</li>
     *   <li>Configura un DTO inválido (con un campo nulo para provocar error).</li>
     * </ul>
     */
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        requestValidator = new RequestValidator(validator);

        validDto = new StatsDTO();
        validDto.setTotalContactoClientes(250);
        validDto.setMotivoReclamo(25);
        validDto.setMotivoGarantia(10);
        validDto.setMotivoDuda(100);
        validDto.setMotivoCompra(100);
        validDto.setMotivoFelicitaciones(7);
        validDto.setMotivoCambio(8);
        validDto.setHash("5484062a4be1ce5645eb414663e14f59");

        invalidDto = new StatsDTO();
        invalidDto.setTotalContactoClientes(250);
        invalidDto.setMotivoReclamo(25);
        invalidDto.setMotivoGarantia(10);
        invalidDto.setMotivoDuda(100);
        invalidDto.setMotivoCompra(100);
        invalidDto.setMotivoFelicitaciones(7);
        invalidDto.setMotivoCambio(null);
        invalidDto.setHash("5484062a4be1ce5645eb414663e14f59");
    }

    /**
     * Verifica que un DTO válido pase la validación sin errores.
     */
    @Test
    void shouldPassValidationWhenDtoIsValid() {

        Mono<StatsDTO> result = requestValidator.validate(validDto);


        StepVerifier.create(result)
                .expectNext(validDto)
                .verifyComplete();
    }

    /**
     * Verifica que un DTO inválido lance una IllegalArgumentException durante la validación.
     */
    @Test
    void shouldThrowExceptionWhenDtoIsInvalid() {

        Mono<StatsDTO> result = requestValidator.validate(invalidDto);

        StepVerifier.create(result)
                .expectError(IllegalArgumentException.class)
                .verify();
    }
}
