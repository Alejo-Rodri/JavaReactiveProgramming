package sec11;

import common.Util;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/*
    repeat operator resubscribes when receives the complete signal.
    it doesn't like the error signal.
 */
public class Lec01Repeat {
    public static void main(String[] args) {

        demo4();

        Util.sleepSeconds(10);
    }

    private static void demo1() {
        // si hacemos esta vaina sucede que no hay manejo de backpressure
        // antes de siquiera obtener el resultado de la primera iteracion ya se esta
        // pidiendo la segunda y la tercera
//        for (int i = 0; i < 3; i++) {
//            mono.subscribe(subscriber);
//        }
        var subscriber = Util.subscriber();
        // en cambio con repeat es secuencial y tiene backpressure
        getCountryName().repeat(3)
                //.repeat() // cuando ponemos el repeat al mono se vuelve un flux
                .subscribe(subscriber);
    }

    private static void demo2() {
        getCountryName()
                .repeat()
                .takeUntil(c -> c.equalsIgnoreCase("canada"))
                .subscribe(Util.subscriber());
    }

    private static void demo3() {
        var atomicInt = new AtomicInteger();
        getCountryName()
                // detiene el repeat en base a un condicional
                // detiene el repeat con base en un condicional
                .repeat(() -> atomicInt.incrementAndGet() < 3)
                .subscribe(Util.subscriber());
    }

    private static void demo4() {
        getCountryName()
                // este es util para por ej evitar sobrecargar una api de peticiones
                .repeatWhen(flux -> flux.delayElements(Duration.ofSeconds(2)))
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getCountryName() {
        return Mono.fromSupplier(() -> Util.getFaker().country().name());   // non-blocking IO
    }
}
