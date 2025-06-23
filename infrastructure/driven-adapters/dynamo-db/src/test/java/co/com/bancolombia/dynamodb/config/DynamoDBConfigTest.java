package co.com.bancolombia.dynamodb.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.metrics.MetricPublisher;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Pruebas unitarias para la clase de configuración {@link DynamoDBConfig}.
 *
 * <p>Verifica la correcta creación de los beans de DynamoDB para los diferentes perfiles y el cliente mejorado.</p>
 */
@ExtendWith(MockitoExtension.class)
class DynamoDBConfigTest {

    @Mock
    private MetricPublisher publisher;

    @Mock
    private DynamoDbAsyncClient dynamoDbAsyncClient;

    private final DynamoDBConfig dynamoDBConfig = new DynamoDBConfig();

    /**
     * Verifica que el bean de DynamoDB para entorno local se cree correctamente.
     */
    @Test
    void testAmazonDynamoDB() {

        DynamoDbAsyncClient result = dynamoDBConfig.amazonDynamoDB(
                "http://aws.dynamo.test",
                "region",
                publisher);

        assertNotNull(result);
    }

    /**
     * Verifica que el bean de DynamoDB para ambientes dev/cer/pdn se cree correctamente.
     */
    @Test
    void testAmazonDynamoDBAsync() {

        DynamoDbAsyncClient result = dynamoDBConfig.amazonDynamoDBAsync(
                publisher,
                "region");

        assertNotNull(result);
    }

    /**
     * Verifica que el bean del cliente mejorado de DynamoDB se cree correctamente.
     */
    @Test
    void testGetDynamoDbEnhancedAsyncClient() {
        DynamoDbEnhancedAsyncClient result = dynamoDBConfig.getDynamoDbEnhancedAsyncClient(dynamoDbAsyncClient);

        assertNotNull(result);
    }
}
