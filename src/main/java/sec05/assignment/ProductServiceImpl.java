package sec05.assignment;

import reactor.core.publisher.Mono;


public class ProductServiceImpl implements ProductService {
    private final Client client = new Client();

    @Override
    public Mono<String> getProductName(int id) {
        return client.productService(id);
    }

//    private Mono<Product> getProductNameService(int id) {
//        return Mono.defer(() -> {
//            for (Product product : products) {
//                Util.sleepSeconds(1);
//                if (product.id() == id) return Mono.just(product);
//            }
//
//            return Mono.empty();
//        });
//    }
//
//    private Mono<Product> getProductNameFallbackTimeout() {
//        return Mono.just(new Product(5, "fallbackTimeout", 5000));
//    }
//
//    private Mono<Product> getProductNameFallbackEmpty() {
//        return Mono.just(new Product(5, "fallbackEmpty", 5000));
//    }
}
