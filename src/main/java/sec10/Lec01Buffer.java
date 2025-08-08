package sec10;

import common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    Collect items based on given internal or size
 */
public class Lec01Buffer {
    public static void main(String[] args) {
        demo4();

        Util.sleepSeconds(60);
    }

    public static void demo1() {
        eventStream()
                .buffer()   // by default integer max value  or the source complete
                .subscribe(Util.subscriber("buffer"));
    }

    public static void demo2() {
        eventStream()
                .buffer(3)   // every n items
                .subscribe(Util.subscriber("buffer"));
    }

    public static void demo3() {
        eventStream()
                .buffer(Duration.ofMillis(500))   // every t seconds
                .subscribe(Util.subscriber("buffer"));
    }

    public static void demo4() {
        eventStream()
                .bufferTimeout(3, Duration.ofSeconds(1))   // every n items or every t seconds
                .subscribe(Util.subscriber("buffer"));
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(200))
                .take(10)
                .concatWith(Flux.never())
                .map(i -> "event-" + i);
    }
}
