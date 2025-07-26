package sec07;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec05PublishOn {
    private static final Logger log = LoggerFactory.getLogger(Lec05PublishOn.class);
    public static void main(String[] args) {
        // entonces todo lo que va hacia arriba se ejecuta en el mismo thread
        // pero lo que va hacia abajo ciertamente se puede separar?
        var flux = Flux.create(fluxSink -> {
                    for (int i = 0; i < 3; i++) {
                        log.info("generating {}", i);
                        fluxSink.next(i);
                    }

                    fluxSink.complete();
                })
                .publishOn(Schedulers.parallel())
                .doOnNext(value -> log.info("value: {}", value))
                .doFirst(() -> log.info("first1"))
                .publishOn(Schedulers.boundedElastic())
                // para abajo hace su tarea con el thread del publish on,
                // esto quiere decir que pasara al subscriber a otro thread?
                // bueno el subscriber lo recibira en el nuevo thread
                .doFirst(() -> log.info("first2"));

        // para arriba hace su tarea con el thread del runnable
        flux.subscribe(Util.subscriber("subscribeOn1"));

        Util.sleepSeconds(5);
    }
}
