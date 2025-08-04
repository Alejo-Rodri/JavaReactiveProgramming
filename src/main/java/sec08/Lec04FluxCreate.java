package sec08;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class Lec04FluxCreate {
    private static final Logger log = LoggerFactory.getLogger(Lec04FluxCreate.class);

    public static void main(String[] args) {
        System.setProperty("reactor.bufferSize.small", "16");

        System.setProperty("reactor.schedulers.defaultBoundedElasticOnVirtualThreads", "true");

        // generate emite segun la demanda del subscriptor
        // create emite segun lo que programemos

        var producer = Flux.create(sink -> {
            // genera los 500 elementos pero solo encola 16 y como el subscriptor se demora
            // en consumirlos se estan generando los datos aunque aun no se puedan emitir
            // this is wrong! strive for lazy
                    for (int i = 0; i < 500 && !sink.isCancelled(); i++) {
                        log.info("generating: {}", i);
                        sink.next(i);
                        Util.sleep(Duration.ofMillis(50));
                    }

                    sink.complete();
                })
                .cast(Integer.class)
                .subscribeOn(Schedulers.parallel());

        producer
                .publishOn(Schedulers.boundedElastic())
                .map(Lec04FluxCreate::timeConsumingTask)
                .subscribe();

        Util.sleepSeconds(60);
    }

    private static int timeConsumingTask(int i) {
        log.info("received: {}", i);
        Util.sleepSeconds(1);
        return i;
    }
}
