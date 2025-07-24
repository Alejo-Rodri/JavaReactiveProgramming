package sec06;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec04HotPublisherCache {
    private static final Logger log = LoggerFactory.getLogger(Lec04HotPublisherCache.class);

    public static void main(String[] args) {
        // con replay se puede cachear los ultimos n elementos, si no se especifica cachea max integer
        var stock = stockStream().replay(2).autoConnect(0);

        Util.sleepSeconds(1);
        log.info("sam is joining...");
        stock.subscribe(Util.subscriber("sam"));
        Util.sleepSeconds(3);

        log.info("thom is joining...");
        stock.subscribe(Util.subscriber("thom"));
        Util.sleepSeconds(5);

        log.info("tiger is joining...");
        stock.subscribe(Util.subscriber("tiger"));

        Util.sleepSeconds(11);
    }

    private static Flux<Integer> stockStream() {
        return Flux.generate(synchronousSink ->
                        synchronousSink.next(Util.getFaker().random().nextInt(1, 100)))
                .doOnNext(price -> log.info("emitting price: {}", price))
                .delayElements(Duration.ofSeconds(3))
                .cast(Integer.class);
    }
}
