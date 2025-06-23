package co.com.bancolombia.dynamodb.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.auth.credentials.WebIdentityTokenFileCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.metrics.MetricPublisher;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import java.net.URI;

/**
 * Configuración de beans para la integración con DynamoDB.
 *
 * <p>Define la creación de clientes asíncronos de DynamoDB para diferentes perfiles de ejecución
 * (local, desarrollo, certificación, producción) y expone el cliente mejorado para operaciones reactivas.</p>
 */
@Configuration
@Slf4j
public class DynamoDBConfig {

    /**
     * Crea un cliente asíncrono de DynamoDB configurado para entorno local.
     *
     * @param endpoint Endpoint local de DynamoDB
     * @param region Región AWS simulada
     * @param publisher Publicador de métricas
     * @return Cliente asíncrono de DynamoDB para pruebas locales
     */
    @Bean
    @Profile({"local"})
    public DynamoDbAsyncClient amazonDynamoDB(@Value("${aws.dynamodb.endpoint}") String endpoint,
                                              @Value("${aws.region}") String region,
                                              MetricPublisher publisher) {
        log.info("Starting DynamoDB Local with the following configuration:");
        log.info("Using DynamoDB Local at endpoint: {}", endpoint);
        log.info("Using AWS region: {}", region);
        return DynamoDbAsyncClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("local", "local")))
                .region(Region.of(region))
                .endpointOverride(URI.create(endpoint))
                .overrideConfiguration(o -> o.addMetricPublisher(publisher))
                .build();
    }

    /**
     * Crea un cliente asíncrono de DynamoDB para ambientes de desarrollo, certificación y producción.
     *
     * @param publisher Publicador de métricas
     * @param region Región AWS
     * @return Cliente asíncrono de DynamoDB autenticado con Web Identity
     */
    @Bean
    @Profile({"dev", "cer", "pdn"})
    public DynamoDbAsyncClient amazonDynamoDBAsync(MetricPublisher publisher, @Value("${aws.region}") String region) {
        return DynamoDbAsyncClient.builder()
                .credentialsProvider(WebIdentityTokenFileCredentialsProvider.create())
                .region(Region.of(region))
                .overrideConfiguration(o -> o.addMetricPublisher(publisher))
                .build();
    }

    /**
     * Expone el cliente mejorado de DynamoDB para operaciones reactivas.
     *
     * @param client Cliente asíncrono de DynamoDB base
     * @return Cliente mejorado de DynamoDB
     */
    @Bean
    public DynamoDbEnhancedAsyncClient getDynamoDbEnhancedAsyncClient(DynamoDbAsyncClient client) {
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(client)
                .build();
    }

}
