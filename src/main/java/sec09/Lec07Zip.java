package sec09;

import common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    Zip
    - we will subscribe to all the producers at the same time
    - all or nothing
    - all producers will have to emit an item
 */
public class Lec07Zip {
    record Car(int id, String body, String engine, String tires) {}

    public static void main(String[] args) {
        Flux.zip(getBody(), id(), getEngine(), getTires())
                .map(t -> new Car(t.getT2(), t.getT1(), t.getT3(), t.getT4()))
                .subscribe(Util.subscriber("zip"));

        Util.sleepSeconds(5);
    }

    public static Flux<String> getBody() {
        return Flux.range(1, 5)
                .map(i -> "body-" + i)
                .transform(Util.fluxLogger("getBody"))
                .delayElements(Duration.ofMillis(100));
    }

    public static Flux<String> getEngine() {
        return Flux.range(1, 3)
                .map(i -> "engine-" + i)
                .transform(Util.fluxLogger("getEngine"))
                .delayElements(Duration.ofMillis(200));
    }

    public static Flux<String> getTires() {
        return Flux.range(1, 10)
                .map(i -> "tires-" + i)
                .transform(Util.fluxLogger("getTires"))
                .delayElements(Duration.ofMillis(75));
    }

    public static Flux<Integer> id() {
        return Flux.range(1, 10)
                .transform(Util.fluxLogger("id"));
                //.delayElements(Duration.ofMillis(100));
    }
}
