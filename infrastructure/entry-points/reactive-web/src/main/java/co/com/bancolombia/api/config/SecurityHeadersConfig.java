package co.com.bancolombia.api.config;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Filtro web que agrega cabeceras de seguridad HTTP a todas las respuestas.
 *
 * <p>Incluye políticas de seguridad como Content-Security-Policy, Strict-Transport-Security,
 * X-Content-Type-Options, entre otras, para proteger la aplicación frente a ataques comunes.</p>
 */
@Component
public class SecurityHeadersConfig implements WebFilter {

    /**
     * Aplica las cabeceras de seguridad a la respuesta HTTP antes de continuar con la cadena de filtros.
     *
     * @param exchange El intercambio web actual
     * @param chain La cadena de filtros web
     * @return Mono que representa la continuación de la cadena de filtros
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        HttpHeaders headers = exchange.getResponse().getHeaders();
        headers.set("Content-Security-Policy", "default-src 'self'; frame-ancestors 'self'; form-action 'self'");
        headers.set("Strict-Transport-Security", "max-age=31536000;");
        headers.set("X-Content-Type-Options", "nosniff");
        headers.set("Server", "");
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        headers.set("Referrer-Policy", "strict-origin-when-cross-origin");
        return chain.filter(exchange);
    }
}
