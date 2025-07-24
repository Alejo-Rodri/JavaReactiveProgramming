package sec06;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec02HotPublisher {
    private static final Logger log = LoggerFactory.getLogger(Lec02HotPublisher.class);

    // con share() hacemos el publisher hot
    public static void main(String[] args) {
        var movie = movieStream()
                //.publish().refCount(1) // es lo mismo que share() solo que permite
                // especificar la cantidad de subscriptores que necesita para mantenerse en emision
                .share();

        // cuando el publisher no tiene subscriptores se detiene
        movie
                .take(1)
                .subscribe(Util.subscriber("sam"));
        Util.sleepSeconds(3);

        // cuando un subscriptor cancela no afecta a la emicion del publisher y sigue emitiendo
        movie
                .take(3)
                .subscribe(Util.subscriber("thom"));

        // cuando el publisher deja de emitir, el proximo subscriber recibira
        // la transmision desde el inicio NO desde donde lo dejo el ultimo
        Util.sleepSeconds(11);
    }

    private static Flux<String> movieStream() {
        return Flux.generate(
                        () -> {
                            log.info("received the request");
                            return 1;
                        },
                        (state, sink) -> {
                            var scene = "movie scene: " + state;
                            log.info("playing scene: {}", scene);
                            sink.next(scene);
                            return ++state;
                        }
                )
                .take(10)
                .delayElements(Duration.ofSeconds(1))
                .cast(String.class);
    }
}
