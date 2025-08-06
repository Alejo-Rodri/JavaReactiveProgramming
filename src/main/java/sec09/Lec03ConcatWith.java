package sec09;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec03ConcatWith {
    private static final Logger log = LoggerFactory.getLogger(Lec03ConcatWith.class);

    public static void main(String[] args) {
        demo3();

        Util.sleepSeconds(3);
    }

    private static void demo1() {
        producer1()
                .concatWithValues(-1, 2)
                //.take(3)
                .subscribe(Util.subscriber("demo1"));
    }

    private static void demo2() {
        producer1()
                .concatWith(producer2())
                .take(2)
                .subscribe(Util.subscriber("demo2"));
    }

    private static void demo3() {
        Flux.concat(producer1(), producer2(), producer1())
                .subscribe(Util.subscriber("demo3"));
    }

    private static Flux<Integer> producer1() {
        return Flux.just(1, 2, 3)
                .doOnSubscribe(s -> log.info("subscribing to producer1"))
                .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer2() {
        return Flux.just(51, 52, 53)
                .doOnSubscribe(s -> log.info("subscribing to producer2"))
                .delayElements(Duration.ofMillis(10));
    }
}
