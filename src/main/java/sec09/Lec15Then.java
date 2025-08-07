package sec09;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

/*
    "then" could be helpful when we're not interested in the result of a publisher or
    we need to have sequential execution of asynchronous tasks
 */
public class Lec15Then {
    private static final Logger log = LoggerFactory.getLogger(Lec15Then.class);

    public static void main(String[] args) {
        var records = List.of("a", "b", "c");
        saveRecords(records)
                // to use when we're interested in the completed signal
                //.then()
                .then(sendNotification(records))
                .subscribe(Util.subscriber("then"));

        Flux<String> flux = Flux.just("a", "b", "c");
        flux.startWith(flux)
                        .subscribe(Util.subscriber("test"));

        Util.sleepSeconds(5);
    }

    private static Flux<String> saveRecords(List<String> records) {
        return Flux.fromIterable(records)
                .map(record -> "saved: " + record)
                .delayElements(Duration.ofMillis(500));
    }

    private static Mono<Void> sendNotification(List<String> records) {
        return Mono.fromRunnable(() -> log.info("all these records {} were saved successfully.", records));
    }
}
