package sec06.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public class RevenueService {
    private static final Logger log = LoggerFactory.getLogger(RevenueService.class);
    private final Map<String, Integer> database;

    public RevenueService() {
        database = new HashMap<>();
    }

    public UnaryOperator<Flux<Product>> addInventory() {
        return tFlux -> tFlux
                .doOnNext(product -> {
                    database.put(product.category(), product.price());
                    log.info("Category: {}, Price: {}", product.category(), database.get(product.category()));
                }).delayElements(Duration.ofSeconds(2));
    }
}
