package sec02;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec10MonoDefer {
    private static final Logger log = LoggerFactory.getLogger(Lec10MonoDefer.class);

    public static void main(String[] args) {

        createPublisher();
                //.subscribe(Util.subscriber("eager_publisher_creation"));

        // la magia esta en que solo se creara el publisher cuando se subscriba el consumidor :)
        Mono.defer(() -> createPublisher());
                //.subscribe(Util.subscriber("lazy_publisher_creation"));
    }

    // en este caso crear el publisher es una operacion costosa que consume
    // por lo tanto, deberia ser lazy, crearse solo cuando sea necesario
    private static Mono<Integer> createPublisher() {
        log.info("creating publisher");
        var list = List.of(1, 2, 3);
        Util.sleepSeconds(1);
        return Mono.fromSupplier(() -> sum(list));
    }

    // time-consuming business logic
    private static int sum(List<Integer> list) {
        log.info("finding the sum of {}", list);
        return list.stream().mapToInt(a -> a).sum();
    }
}
