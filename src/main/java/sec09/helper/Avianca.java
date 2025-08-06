package sec09.helper;

import common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Avianca {
    private static final String AIRLINE = "Avianca";

    public static Flux<Flight> getFLights() {
        return Flux.range(1, Util.getFaker().random().nextInt(2, 10))
                .delayElements(Duration.ofMillis(Util.getFaker().random().nextInt(200, 1000)))
                .map(i -> new Flight(AIRLINE, Util.getFaker().random().nextInt(300, 1000)))
                .transform(Util.fluxLogger(AIRLINE));
    }
}
