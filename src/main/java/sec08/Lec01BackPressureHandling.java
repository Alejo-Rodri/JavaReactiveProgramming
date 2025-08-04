package sec08;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.concurrent.Queues;

public class Lec01BackPressureHandling {
    private static final Logger log = LoggerFactory.getLogger(Lec01BackPressureHandling.class);

    public static void main(String[] args) {
        // System.getProperty("reactor.bufferSize.small", "256"); // este es el valor por defecto del buffer; 256
        System.setProperty("reactor.bufferSize.small", "16");

        System.setProperty("reactor.schedulers.defaultBoundedElasticOnVirtualThreads", "true");
        // el publisher genera y almacena sus elementos en una cola, es subscriptor consume de esta cola,
        // si la cola se llena el productor deja de emitir datos ya que el subscriptor no los puede procesar
        // si el 75% de la cola se vacia el productor vuelve a emitir datos. easy and elegant
        var producer = Flux.generate(
                        () -> 1,
                        (state, sink) -> {
                            log.info("generating {}", state);
                            sink.next(state);
                            return ++state;
                        }
                )
                .cast(Integer.class)
                .subscribeOn(Schedulers.parallel());

        producer
                .publishOn(Schedulers.boundedElastic())
                .map(Lec01BackPressureHandling::timeConsumingTask)
                .subscribe(Util.subscriber("backpressure"));

        Util.sleepSeconds(60);
    }

    private static int timeConsumingTask(int i) {
        Util.sleepSeconds(1);
        return i;
    }
}
