package co.com.bancolombia.dynamodb.helper;

import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.SdkPublisher;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.function.Function;

/**
 * Clase abstracta que centraliza operaciones genéricas de persistencia y consulta en DynamoDB.
 *
 * <p>Provee métodos para guardar, obtener, eliminar y consultar entidades de forma reactiva,
 * utilizando el cliente asíncrono mejorado de DynamoDB y un mapper para conversión de modelos.</p>
 *
 * @param <E> Tipo de entidad de dominio
 * @param <K> Tipo de clave primaria
 * @param <V> Tipo de entidad de base de datos (adaptador)
 */
public abstract class TemplateAdapterOperations<E, K, V> {
    private final Class<V> dataClass;
    private final Function<V, E> toEntityFn;
    protected ObjectMapper mapper;
    private final DynamoDbAsyncTable<V> table;
    private final DynamoDbAsyncIndex<V> tableByIndex;

    @SuppressWarnings("unchecked")
    protected TemplateAdapterOperations(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient,
                                        ObjectMapper mapper,
                                        Function<V, E> toEntityFn,
                                        String tableName,
                                        String... index) {
        this.toEntityFn = toEntityFn;
        this.mapper = mapper;
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.dataClass = (Class<V>) genericSuperclass.getActualTypeArguments()[2];
        table = dynamoDbEnhancedAsyncClient.table(tableName, TableSchema.fromBean(dataClass));
        tableByIndex = index.length > 0 ? table.index(index[0]) : null;
    }

    /**
     * Guarda una entidad en la tabla DynamoDB.
     *
     * @param model Entidad de dominio a guardar
     * @return Mono que emite la entidad guardada
     */
    public Mono<E> save(E model) {
        return Mono.fromFuture(table.putItem(toEntity(model))).thenReturn(model);
    }

    /**
     * Obtiene una entidad por su clave primaria.
     *
     * @param id Clave primaria de la entidad
     * @return Mono que emite la entidad encontrada o vacío si no existe
     */
    public Mono<E> getById(K id) {
        return Mono.fromFuture(table.getItem(Key.builder()
                        .partitionValue(AttributeValue.builder().s((String) id).build())
                        .build()))
                .map(this::toModel);
    }

    /**
     * Elimina una entidad de la tabla DynamoDB.
     *
     * @param model Entidad de dominio a eliminar
     * @return Mono que emite la entidad eliminada
     */
    public Mono<E> delete(E model) {
        return Mono.fromFuture(table.deleteItem(toEntity(model))).map(this::toModel);
    }

    /**
     * Realiza una consulta sobre la tabla DynamoDB usando una expresión de consulta.
     *
     * @param queryExpression Expresión de consulta
     * @return Mono que emite la lista de entidades encontradas
     */
    public Mono<List<E>> query(QueryEnhancedRequest queryExpression) {
        PagePublisher<V> pagePublisher = table.query(queryExpression);
        return listOfModel(pagePublisher);
    }

    /**
     * Realiza una consulta sobre un índice secundario de la tabla DynamoDB.
     *
     * @param queryExpression Expresión de consulta
     * @param index Nombre(s) del índice secundario
     * @return Mono que emite la lista de entidades encontradas
     */
    public Mono<List<E>> queryByIndex(QueryEnhancedRequest queryExpression, String... index) {
        DynamoDbAsyncIndex<V> queryIndex = index.length > 0 ? table.index(index[0]) : tableByIndex;

        SdkPublisher<Page<V>> pagePublisher = queryIndex.query(queryExpression);
        return listOfModel(pagePublisher);
    }

    /**
     * @return Mono<List < E>>
     * @implNote Bancolombia does not suggest the Scan function for DynamoDB tables due to the low performance resulting
     * from the design of the database engine (Key value). Optimize the query using Query, GetItem or BatchGetItem
     * functions, and if necessary, consider the Single-Table Design pattern for DynamoDB.
     * @deprecated
     */
    @Deprecated(forRemoval = true)
    public Mono<List<E>> scan() {
        PagePublisher<V> pagePublisher = table.scan();
        return listOfModel(pagePublisher);
    }

    private Mono<List<E>> listOfModel(PagePublisher<V> pagePublisher) {
        return Mono.from(pagePublisher).map(page -> page.items().stream().map(this::toModel).toList());
    }

    private Mono<List<E>> listOfModel(SdkPublisher<Page<V>> pagePublisher) {
        return Mono.from(pagePublisher).map(page -> page.items().stream().map(this::toModel).toList());
    }

    protected V toEntity(E model) {
        return mapper.map(model, dataClass);
    }

    protected E toModel(V data) {
        return data != null ? toEntityFn.apply(data) : null;
    }
}