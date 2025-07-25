package sec05.assignment;

import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<String> getProductName(int id);
}
