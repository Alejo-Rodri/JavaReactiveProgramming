package sec05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec05Subscribe {
    private static final Logger log = LoggerFactory.getLogger(Lec05Subscribe.class);
    public static void main(String[] args) {
        // tambien se pueden emitir datos de esta forma
        // es como cuando se implementaban los metodos en la funcion lambda pasada al argumento de la funcion
        Flux.range(1, 10)
                .doOnNext(item -> log.info("item: {}", item))
                .doOnComplete(() -> log.info("completed!"))
                .doOnError(e -> log.error("error", e))
                .subscribe();
    }
}
