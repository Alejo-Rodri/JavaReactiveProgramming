package sec09.helper;

import common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Siberia {
    private static final String AIRLINE = "Siberia";

    public static Flux<Flight> getFLights() {
        return Flux.range(1, Util.getFaker().random().nextInt(5, 10))
                .delayElements(Duration.ofMillis(Util.getFaker().random().nextInt(200, 1200)))
                .map(i -> new Flight(AIRLINE, Util.getFaker().random().nextInt(300, 1200)))
                .transform(Util.fluxLogger(AIRLINE));
    }
}
