package sec09.helper;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class Despegar {
    public static Flux<Flight> getFLights() {
        return Flux.merge(
                        Avianca.getFLights(),
                        Latam.getFLights(),
                        Siberia.getFLights()
                )
                .take(Duration.ofSeconds(2));
    }
}
