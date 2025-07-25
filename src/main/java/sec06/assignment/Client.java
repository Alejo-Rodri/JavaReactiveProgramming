package sec06.assignment;

import common.AbstractHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Client extends AbstractHttpClient {
    private static final Logger log = LoggerFactory.getLogger(Client.class);

    public Flux<Product> getProductStream() {
        return this.httpClient.get()
                .uri("/demo04/orders/stream")
                .responseContent()
                .asString()
                .map(this::parseStringToProduct)
                .doOnNext(product -> log.info("Product: {}", product))
                .publish().refCount(2);
    }

    private Product parseStringToProduct(String rawProduct) {
        String[] attributes = rawProduct.split(":");
        return new Product(
                attributes[0],
                attributes[1],
                Integer.parseInt(attributes[2]),
                Integer.parseInt(attributes[3])
        );
    }
}
