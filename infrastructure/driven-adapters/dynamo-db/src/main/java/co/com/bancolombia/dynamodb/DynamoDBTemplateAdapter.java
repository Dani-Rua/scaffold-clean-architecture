package co.com.bancolombia.dynamodb;

import co.com.bancolombia.dynamodb.helper.TemplateAdapterOperations;
import co.com.bancolombia.model.stats.Stats;
import co.com.bancolombia.model.stats.gateways.StatsRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;

/**
 * Adaptador para operaciones de persistencia en DynamoDB de la entidad {@link Stats}.
 *
 * <p>Implementa el gateway {@link StatsRepository} de la capa de dominio utilizando el cliente asíncrono de DynamoDB
 * y el template genérico {@link TemplateAdapterOperations} para mapear entre el modelo de dominio y el modelo de la base de datos.</p>
 *
 * <p>La tabla utilizada es <b>stats-table</b>.</p>
 */
@Repository
public class DynamoDBTemplateAdapter extends TemplateAdapterOperations<Stats /*domain model*/, String, StatsEntity /*adapter model*/> implements StatsRepository/* implements Gateway from domain */ {

    /**
     * Constructor del adaptador DynamoDB.
     *
     * @param connectionFactory Cliente asíncrono de DynamoDB
     * @param mapper            Mapper para convertir entre modelos de dominio y de base de datos
     */
    public DynamoDBTemplateAdapter(DynamoDbEnhancedAsyncClient connectionFactory, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(connectionFactory, mapper, d -> mapper.map(d, Stats.class /*domain model*/), "stats-table" /*index is optional*/);
    }

    /**
     * Guarda una entidad de estadísticas en DynamoDB.
     *
     * @param stats Entidad de estadísticas a guardar
     * @return Mono que emite la entidad guardada o un error si falla la operación
     */
    @Override
    public Mono<Stats> saveStats(Stats stats) {
        return super.save(stats);
    }
}
