package co.com.bancolombia.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

/**
 * DTO para la recepción de estadísticas de contacto de clientes en la API.
 *
 * <p>Incluye validaciones para asegurar que los datos recibidos sean correctos y completos.</p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatsDTO {

    /**
     * Total de contactos de clientes.
     * Debe ser un número positivo y no nulo.
     */
    @NotNull(message = "totalContactoClientes no puede ser null")
    @Min(value = 0, message = "totalContactoClientes debe ser un numero positivo")
    private Integer totalContactoClientes;

    /**
     * Cantidad de contactos por reclamo.
     * Debe ser un número positivo y no nulo.
     */
    @NotNull(message = "motivoReclamo no puede ser null")
    @Min(value = 0, message = "motivoReclamo debe ser un número positivo")
    private Integer motivoReclamo;

    /**
     * Cantidad de contactos por garantía.
     * Debe ser un número positivo y no nulo.
     */
    @NotNull(message = "motivoGarantia no puede ser null")
    @Min(value = 0, message = "motivoGarantia debe ser un número positivo")
    private Integer motivoGarantia;

    /**
     * Cantidad de contactos por duda.
     * Debe ser un número positivo y no nulo.
     */
    @NotNull(message = "motivoDuda no puede ser null")
    @Min(value = 0, message = "motivoDuda debe ser un número positivo")
    private Integer motivoDuda;

    /**
     * Cantidad de contactos por compra.
     * Debe ser un número positivo y no nulo.
     */
    @NotNull(message = "motivoCompra no puede ser null")
    @Min(value = 0, message = "motivoCompra debe ser un número positivo")
    private Integer motivoCompra;

    /**
     * Cantidad de contactos por felicitaciones.
     * Debe ser un número positivo y no nulo.
     */
    @NotNull(message = "motivoFelicitaciones no puede ser null")
    @Min(value = 0, message = "motivoFelicitaciones debe ser un número positivo")
    private Integer motivoFelicitaciones;

    /**
     * Cantidad de contactos por cambio.
     * Debe ser un número positivo y no nulo.
     */
    @NotNull(message = "motivoCambio no puede ser null")
    @Min(value = 0, message = "motivoCambio debe ser un número positivo")
    private Integer motivoCambio;

    /**
     * Hash MD5 generado a partir de los campos anteriores.
     * Debe ser un string no vacío y cumplir con el patrón de un MD5 válido.
     */
    @NotBlank(message = "hash no puede ser null o vacío")
    @Pattern(regexp = "^[a-f0-9]{32}$", message = "hash debe ser un MD5 válido")
    private String hash;
}
