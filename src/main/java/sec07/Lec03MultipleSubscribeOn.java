package sec07;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec03MultipleSubscribeOn {
    private static final Logger log = LoggerFactory.getLogger(Lec03MultipleSubscribeOn.class);

    public static void main(String[] args) {
        // se puede tener un monton de subscribeOn en la misma pipeline de reactor
        var flux = Flux.create(fluxSink -> {
                    for (int i = 0; i < 3; i++) {
                        log.info("generating {}", i);
                        fluxSink.next(i);
                    }

                    fluxSink.complete();
                })
                // se mantiene en el boundedElastic 2, no se pasa a un nuevo hilo
                .subscribeOn(Schedulers.immediate())
                .subscribeOn(Schedulers.parallel())
                .doOnNext(value -> log.info("value: {}", value))
                .doFirst(() -> log.info("first1"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("first2"));

        flux.subscribe(Util.subscriber("subscribeOn1"));

        Util.sleepSeconds(5);
    }

    // immediate es como el unaryOperator, lo usamos cuando necesitamos pasar un
    // Scheduler/hilo como argumento por ej, pero en realidad nos queremos mantener en el hilo actual
    // immediate nos permite hacer esto
}

