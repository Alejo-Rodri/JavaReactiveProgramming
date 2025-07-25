package sec06.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class InventoryService {
    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);
    private final Map<String, Integer> database;

    public InventoryService() {
        database = new HashMap<>();
    }

    public UnaryOperator<Flux<sec06.assignment.Product>> addInventory() {
        return tFlux -> tFlux
                .doOnNext(product -> {
                    if (Objects.isNull(database.get(product.item())))
                        database.put(
                                product.item(),
                                500 - product.quantity()
                        );
                    else
                        database.compute(product.item(), (k, pastValue) -> pastValue - product.quantity());

                    log.info("Item: {}, Quantity: {}", product.item(), database.get(product.item()));
                });
    }
}
