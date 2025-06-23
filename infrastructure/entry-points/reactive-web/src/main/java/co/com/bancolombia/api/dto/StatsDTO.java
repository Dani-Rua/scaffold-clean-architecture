package co.com.bancolombia.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatsDTO {

    @NotNull(message = "totalContactoClientes no puede ser null")
    @Min(value = 0, message = "totalContactoClientes debe ser un numero positivo")
    private Integer totalContactoClientes;

    @NotNull(message = "motivoReclamo no puede ser null")
    @Min(value = 0, message = "motivoReclamo debe ser un número positivo")
    private Integer motivoReclamo;

    @NotNull(message = "motivoGarantia no puede ser null")
    @Min(value = 0, message = "motivoGarantia debe ser un número positivo")
    private Integer motivoGarantia;

    @NotNull(message = "motivoDuda no puede ser null")
    @Min(value = 0, message = "motivoDuda debe ser un número positivo")
    private Integer motivoDuda;

    @NotNull(message = "motivoCompra no puede ser null")
    @Min(value = 0, message = "motivoCompra debe ser un número positivo")
    private Integer motivoCompra;

    @NotNull(message = "motivoFelicitaciones no puede ser null")
    @Min(value = 0, message = "motivoFelicitaciones debe ser un número positivo")
    private Integer motivoFelicitaciones;

    @NotNull(message = "motivoCambio no puede ser null")
    @Min(value = 0, message = "motivoCambio debe ser un número positivo")
    private Integer motivoCambio;

    @NotBlank(message = "hash no puede ser null o vacío")
    @Pattern(regexp = "^[a-f0-9]{32}$", message = "hash debe ser un MD5 válido")
    private String hash;
}
