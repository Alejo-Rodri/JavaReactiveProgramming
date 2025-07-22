package sec02;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec07MonoFromRunnable {
    private static final Logger log = LoggerFactory.getLogger(Lec07MonoFromRunnable.class);

    public static void main(String[] args) {
        getProductName(1).subscribe(Util.subscriber("supplier"));
        getProductName(2).subscribe(Util.subscriber("runnable"));
    }

    private static Mono<String> getProductName(int productId) {
        if (productId == 1) return Mono.fromSupplier(() -> Util.getFaker().commerce().productName());

        //return Mono.empty();
        // fromRunnable ejecuta el metodo y luego devuelve Mono.empty() :)
        return Mono.fromRunnable(() -> notifyBusiness(productId));
    }

    private static void notifyBusiness(int productId) {
        log.info("notifying business about unavailable product {}", productId);
    }
}
