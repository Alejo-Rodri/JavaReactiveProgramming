package sec05;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Lec10Transform {
    private static final Logger log = LoggerFactory.getLogger(Lec10Transform.class);

    record Customer(int id, String name) {}
    record PurchaseOrder(String productName, int price, int quantity) {}

    public static void main(String[] args) {
        var isDebugEnabled = true;
        // la idea del transform es meter codigo reutilizable, crear operators/decorators con el
        getCustomers()
                    .transform(isDebugEnabled ? addDebugger() : Function.identity())
                .subscribe(Util.subscriber("customers"));
        getPurchaseOrders()
                .transform(addDebugger())
                .subscribe(Util.subscriber("purchase_orders"));
    }

    private static Flux<Customer> getCustomers() {
        return Flux.range(1, 3)
                .map(i -> new Customer(i, Util.getFaker().name().fullName()));
    }

    private static Flux<PurchaseOrder> getPurchaseOrders() {
        return Flux.range(1, 5)
                .map(i -> new PurchaseOrder(Util.getFaker().commerce().productName(), i, i * 10));
    }

    private static <T> UnaryOperator<Flux<T>> addDebugger() {
        return tFlux -> tFlux
                .doOnNext(i -> log.info("received: {}", i))
                .doOnComplete(() -> log.info("completed!"))
                .doOnError(error -> log.error("error", error));
    }
}
