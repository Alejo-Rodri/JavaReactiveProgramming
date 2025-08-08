package sec10.assignment.groupBy;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class PurchaseOrderService {
    private static final Logger log = LoggerFactory.getLogger(PurchaseOrderService.class);
    private static final Map<String, UnaryOperator<Flux<PurchaseOrder>>> PROCESSOR_MAP = Map.of(
            "Kids", kidsLogic(),
            "Automotive", automotiveLogic()
    );

    public static Flux<PurchaseOrder> emitPurchaseOrders() {
        return Flux.interval(Duration.ofMillis(200))
                .map(i -> PurchaseOrder.create())
                .filter(canProcess())
                .groupBy(PurchaseOrder::category)
                .flatMap(gf -> gf.transform(getProcessor(gf.key())));
    }

    public static Predicate<PurchaseOrder> canProcess() {
        return po -> PROCESSOR_MAP.containsKey(po.category());
    }

    public static UnaryOperator<Flux<PurchaseOrder>> getProcessor(String category) {
        return PROCESSOR_MAP.get(category);
    }

    // agregar una orden gratis
    private static UnaryOperator<Flux<PurchaseOrder>> kidsLogic() {
        return purchaseOrderFlux ->
                purchaseOrderFlux.flatMap(po -> getFreeOrder(po).flux().startWith(po));
    }

    private static Mono<PurchaseOrder> getFreeOrder(PurchaseOrder order) {
        return Mono.fromSupplier(() -> new PurchaseOrder(
                Util.getFaker().commerce().productName() + "-Free",
                "Kids",
                0));
    }

    // agregarle 100 al precio
    private static UnaryOperator<Flux<PurchaseOrder>> automotiveLogic() {
        return purchaseOrderFlux ->
                purchaseOrderFlux.map(purchaseOrder -> new PurchaseOrder(
                        purchaseOrder.item(),
                        purchaseOrder.category(),
                        purchaseOrder.price() + 100
                ));
    }
}
