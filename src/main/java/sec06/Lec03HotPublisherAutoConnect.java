package sec06;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec03HotPublisherAutoConnect {
    private static final Logger log = LoggerFactory.getLogger(Lec03HotPublisherAutoConnect.class);

    public static void main(String[] args) {
        // autoConnect recibe los subscriptores minimos para empezar a emitir
        var movie = movieStream().publish().autoConnect(0);

        Util.sleepSeconds(1);
        movie
                .take(2)
                .subscribe(Util.subscriber("sam"));
        Util.sleepSeconds(3);

        movie
                .take(3)
                .subscribe(Util.subscriber("thom"));
        Util.sleepSeconds(5);

        movie.subscribe(Util.subscriber("tiger"));

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
