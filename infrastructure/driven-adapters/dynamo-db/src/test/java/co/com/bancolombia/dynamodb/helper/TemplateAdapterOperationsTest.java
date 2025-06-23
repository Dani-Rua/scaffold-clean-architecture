package co.com.bancolombia.dynamodb.helper;

import co.com.bancolombia.dynamodb.DynamoDBTemplateAdapter;
import co.com.bancolombia.dynamodb.StatsEntity;
import co.com.bancolombia.model.stats.Stats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import reactor.test.StepVerifier;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias para {@link TemplateAdapterOperations} y su implementación concreta.
 *
 * <p>Verifica el correcto funcionamiento de las operaciones genéricas de persistencia y consulta en DynamoDB usando mocks.</p>
 */
class TemplateAdapterOperationsTest {

    @Mock
    private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private DynamoDbAsyncTable<StatsEntity> customerTable;

    private StatsEntity modelEntity;
    private Stats statsModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(dynamoDbEnhancedAsyncClient.table("stats-table", TableSchema.fromBean(StatsEntity.class)))
            .thenReturn(customerTable);

        modelEntity = new StatsEntity();
        modelEntity.setTimestamp("2024-06-01T10:00:00");
        modelEntity.setTotalContactoClientes(250);
        modelEntity.setMotivoReclamo(25);
        modelEntity.setMotivoGarantia(10);
        modelEntity.setMotivoDuda(100);
        modelEntity.setMotivoCompra(100);
        modelEntity.setMotivoFelicitaciones(7);
        modelEntity.setMotivoCambio(8);
        modelEntity.setHash("5484062a4be1ce5645eb414663e14f59");

        statsModel = new Stats(
            LocalDateTime.parse("2024-06-01T10:00:00"),
            250, 25, 10, 100, 100, 7, 8,
            "5484062a4be1ce5645eb414663e14f59"
        );
    }

    /**
     * Verifica que las propiedades del modelo de entidad no sean nulas tras la construcción.
     */
    @Test
    void modelEntityPropertiesMustNotBeNull() {
        assertNotNull(modelEntity.getTimestamp());
        assertNotNull(modelEntity.getHash());
    }

    /**
     * Verifica que la operación de guardado (save) funcione correctamente en el adaptador.
     */
    @Test
    void testSave() {
        when(mapper.map(statsModel, StatsEntity.class)).thenReturn(modelEntity);
        when(customerTable.putItem(modelEntity)).thenReturn(CompletableFuture.runAsync(() -> {}));
        // El adaptador retorna el modelo de dominio
        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(dynamoDBTemplateAdapter.save(statsModel))
                .expectNext(statsModel)
                .verifyComplete();
    }

    /**
     * Verifica que la operación de obtención por ID (getById) funcione correctamente en el adaptador.
     */
    @Test
    void testGetById() {
        String id = modelEntity.getTimestamp();
        when(customerTable.getItem(
                Key.builder().partitionValue(AttributeValue.builder().s(id).build()).build()))
                .thenReturn(CompletableFuture.completedFuture(modelEntity));
        when(mapper.map(modelEntity, Stats.class)).thenReturn(statsModel);

        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(dynamoDBTemplateAdapter.getById(id))
                .expectNext(statsModel)
                .verifyComplete();
    }

    /**
     * Verifica que la operación de eliminación (delete) funcione correctamente en el adaptador.
     */
    @Test
    void testDelete() {
        when(mapper.map(statsModel, StatsEntity.class)).thenReturn(modelEntity);
        when(customerTable.deleteItem(modelEntity))
                .thenReturn(CompletableFuture.completedFuture(modelEntity));
        when(mapper.map(modelEntity, Stats.class)).thenReturn(statsModel);

        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(dynamoDBTemplateAdapter.delete(statsModel))
                .expectNext(statsModel)
                .verifyComplete();
    }
}