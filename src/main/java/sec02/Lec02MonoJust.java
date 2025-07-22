package sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import sec01.subscriber.SubscriberImpl;

public class Lec02MonoJust {
    private static final Logger log = LoggerFactory.getLogger(Lec02MonoJust.class);

    public static void main(String[] args) {
        // mono.just se usa para crear un publisher rapidamente
        var mono = Mono.just("vinsmoke");
        // no va a mostrar el valor de mono hasta que no estemos subscritos porque es lazy
        log.info("{}", mono);

        var subscriber = new SubscriberImpl();
        mono.subscribe(subscriber);

        // no tiene sentido pedir más de una cosa porque mono solo puede emitir 0 o ninguna
        subscriber.getSubscription().request(3);
    }
}
