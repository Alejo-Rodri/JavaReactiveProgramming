package sec07;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec08Parallel {
    private static final Logger log = LoggerFactory.getLogger(Lec08Parallel.class);

    public static void main(String[] args) {
        System.setProperty("reactor.schedulers.defaultBoundedElasticOnVirtualThreads", "true");
        Flux.range(2, 10)
                //.parallel().runOn(Schedulers.parallel())
                // este parallel es para ejecutar el codigo del flux/mono paralelamente
                // el parallel del Schedulers.parallel() es para especificar que se va a crear un hilo
                // del tipo que se usa para computacion intensiva
                .parallel().runOn(Schedulers.boundedElastic())
                // claro mka esta mierda no funciona porque es en el mismo flux y aunque cree un
                // hilo nuevo importa un culo ya que seguira bloqueado solo que estoy bloqueado asincronamente :)
                // entonces como mierda funciona el runOn y porque lo vuelve no bloqueante si es un mismo flux?
                //.publishOn(Schedulers.boundedElastic())
                .map(Lec08Parallel::process)
                .sequential()
                // con sequential convertimos un parallelFlux (lo que devuelve parallel)
                // nuevamente en un flux/mono secuencial
                .subscribe(Util.subscriber("paralelismo puro y duro"));
        Util.sleepSeconds(20);
    }

    private static Integer process(int num) {
        Util.sleepSeconds(1);
        return num * 2;
    }
}
