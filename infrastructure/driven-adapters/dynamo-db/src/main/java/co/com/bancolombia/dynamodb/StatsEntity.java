package co.com.bancolombia.dynamodb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;


/**
 * Entidad de persistencia para DynamoDB que representa las estadísticas de contacto de clientes.
 *
 * <p>Mapea los campos del modelo de dominio {@link co.com.bancolombia.model.stats.Stats} a la tabla <b>stats-table</b> en DynamoDB.</p>
 *
 * <p>Utiliza anotaciones de AWS SDK para definir el mapeo de atributos y la clave primaria.</p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class StatsEntity {

    private String timestamp;
    private int totalContactoClientes;
    private int motivoReclamo;
    private int motivoGarantia;
    private int motivoDuda;
    private int motivoCompra;
    private int motivoFelicitaciones;
    private int motivoCambio;
    private String hash;

    /**
     * Obtiene el valor del atributo timestamp, que actúa como clave primaria en DynamoDB.
     *
     * @return Timestamp como string (clave primaria)
     */
    @DynamoDbPartitionKey
    @DynamoDbAttribute("timestamp")
    public String getTimestamp() {
        return timestamp;
    }
}
