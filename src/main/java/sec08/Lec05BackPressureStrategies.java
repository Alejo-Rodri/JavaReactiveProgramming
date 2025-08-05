package sec08;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/*
    Backpressure handling by reactor
    - Buffer
    - error
    - Drop
    - Latest
 */
public class Lec05BackPressureStrategies {
    private static final Logger log = LoggerFactory.getLogger(Lec05BackPressureStrategies.class);

    public static void main(String[] args) {
        System.setProperty("reactor.schedulers.defaultBoundedElasticOnVirtualThreads", "true");

        var producer = Flux.create(sink -> {
                            for (int i = 0; i < 500 && !sink.isCancelled(); i++) {
                                log.info("generating: {}", i);
                                sink.next(i);
                                Util.sleep(Duration.ofMillis(50));
                            }

                            sink.complete();
                        }, FluxSink.OverflowStrategy.IGNORE
                )
                .cast(Integer.class)
                .subscribeOn(Schedulers.parallel());

        producer
                //.onBackpressureBuffer(10)
                //.onBackpressureError()
                //.onBackpressureDrop()
                .onBackpressureLatest()
                .log()
                .limitRate(1)
                .publishOn(Schedulers.boundedElastic())
                .map(Lec05BackPressureStrategies::timeConsumingTask)
                .subscribe();

        Util.sleepSeconds(60);
    }

    private static int timeConsumingTask(int i) {
        log.info("received: {}", i);
        Util.sleepSeconds(1);
        return i;
    }
}
