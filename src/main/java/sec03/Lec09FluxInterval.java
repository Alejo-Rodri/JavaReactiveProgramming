package sec03;

import common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec09FluxInterval {
    public static void main(String[] args) {
        // emite lo especificado en un intervalo de tiempo especificado
        Flux.interval(Duration.ofMillis(500))
                .map(i -> Util.getFaker().name().firstName())
                .subscribe(Util.subscriber("interval"));

        Util.sleepSeconds(6);
    }
}
