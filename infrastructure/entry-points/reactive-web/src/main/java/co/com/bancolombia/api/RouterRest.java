package co.com.bancolombia.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Configuración de rutas HTTP para la API REST.
 *
 * <p>Define los endpoints y su asociación con los handlers correspondientes usando programación funcional de Spring WebFlux.</p>
 */
@Configuration
public class RouterRest {
    /**
     * Define la función de ruteo principal de la API.
     *
     * @param handler Handler que gestiona las peticiones HTTP
     * @return RouterFunction con las rutas configuradas
     */
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/v1/stats"), handler::listenPOSTStats);
    }
}
