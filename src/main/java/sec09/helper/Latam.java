package sec09.helper;

import common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Latam {
    private static final String AIRLINE = "Latam";

    public static Flux<Flight> getFLights() {
        return Flux.range(1, Util.getFaker().random().nextInt(3, 5))
                .delayElements(Duration.ofMillis(Util.getFaker().random().nextInt(300, 800)))
                .map(i -> new Flight(AIRLINE, Util.getFaker().random().nextInt(400, 900)))
                .transform(Util.fluxLogger(AIRLINE));
    }
}
