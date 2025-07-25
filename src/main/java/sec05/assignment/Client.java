package sec05.assignment;

import common.AbstractHttpClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Client extends AbstractHttpClient {
    public Mono<String> productService(int id) {
        var defaultPath = "/demo03/product/" + id;
        var timeoutPath = "/demo03/timeout-fallback/product/" + id;
        var emptyPath = "/demo03/empty-fallback/product/" + id;
        return getProductName(defaultPath)
                .timeout(Duration.ofSeconds(2), getProductName(timeoutPath))
                .switchIfEmpty(getProductName(emptyPath));
    }

    private Mono<String> getProductName(String path) {
        return this.httpClient.get()
                .uri(path)
                .responseContent()
                .asString()
                .next();
    }
}
