package co.com.bancolombia.dynamodb;

import co.com.bancolombia.dynamodb.helper.TemplateAdapterOperations;
import co.com.bancolombia.model.stats.Stats;
import co.com.bancolombia.model.stats.gateways.StatsRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;


/**
 * This class is an adapter for DynamoDB operations, extending the TemplateAdapterOperations
 */
@Repository
public class DynamoDBTemplateAdapter extends TemplateAdapterOperations<Stats /*domain model*/, String, StatsEntity /*adapter model*/> implements StatsRepository/* implements Gateway from domain */ {

    public DynamoDBTemplateAdapter(DynamoDbEnhancedAsyncClient connectionFactory, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(connectionFactory, mapper, d -> mapper.map(d, Stats.class /*domain model*/), "stats-table" /*index is optional*/);
    }

    @Override
    public Mono<Stats> saveStats(Stats stats) {
        return super.save(stats);
    }

    @Override
    public Mono<Stats> findByKey(String timestamp) {
        return getById(timestamp.toString());
    }

//    public Mono<List<Stats /*domain model*/>> getEntityBySomeKeys(String partitionKey, String sortKey) {
//        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey, sortKey);
//        return query(queryExpression);
//    }

//    public Mono<List<Stats /*domain model*/>> getEntityBySomeKeysByIndex(String partitionKey, String sortKey) {
//        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey, sortKey);
//        return queryByIndex(queryExpression, "secondary_index" /*index is optional if you define in constructor*/);
//    }

//    private QueryEnhancedRequest generateQueryExpression(String partitionKey, String sortKey) {
//        return QueryEnhancedRequest.builder()
//                .queryConditional(QueryConditional.keyEqualTo(Key.builder().partitionValue(partitionKey).build()))
//                .queryConditional(QueryConditional.sortGreaterThanOrEqualTo(Key.builder().sortValue(sortKey).build()))
//                .build();
//    }
}
