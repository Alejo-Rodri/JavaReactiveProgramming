package sec02;

import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec03MonoSubscribe {
    private static final Logger log = LoggerFactory.getLogger(Lec03MonoSubscribe.class);

    public static void main(String[] args) {
        var mono = Mono.just(1);

        // tenemos que probeerle la implementacion del consumer/subscriber. on osto caso solo implementa onNext, no tiene
        // onComplete u onError
        mono.subscribe(i -> log.info("received: {}", i));

        // aca se implementa onError y onComplete
        // cuando definimos el comportamiento de la interfaz a traves de expresiones lambda se
        // hara automaticamente la peticion/request al publisher
        mono.subscribe(
                i -> log.info("received : {}", i),
                err -> log.error("error", err),
                () -> log.info("completed!")
        );

        // aca estamos definiendo el comportamiento de la subscribcion apenas se subscribe
        mono.subscribe(
                i -> log.info("received : {}", i),
                err -> log.error("error", err),
                () -> log.info("completed!"),
                // subscription -> subscription.request(1)
                // subscription -> test(subscription)
                Lec03MonoSubscribe::test
        );
    }

    public static void test(Subscription subscription) {
        log.info("hello from test");
        subscription.request(1);
    }
}
