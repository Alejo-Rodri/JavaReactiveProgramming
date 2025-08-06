package sec09.assignment;

import common.AbstractHttpClient;
import reactor.core.publisher.Mono;

public class ExternalServiceClient extends AbstractHttpClient {
    public Mono<Product> getProduct(int id) {
        return Mono.zip(
                        getAttribute("/demo05/product/", id),
                        getAttribute("/demo05/review/", id),
                        getAttribute("/demo05/price/", id)
                )
                .map(t -> new Product(t.getT1(), t.getT2(), t.getT3()));
    }

    private Mono<String> getAttribute(String uri, int id) {
        return this.httpClient.get()
                .uri(uri + id)
                .responseContent()
                .asString()
                .next();
    }
}
