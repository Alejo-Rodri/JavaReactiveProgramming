package sec05;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec06ErrorHandling {
    private static final Logger log = LoggerFactory.getLogger(Lec06ErrorHandling.class);

    public static void main(String[] args) {
        //fallbackValue();
//        Mono.error(new RuntimeException("tinguis tiringuis tinguis"))
//                // para un error en especifico
//                .onErrorResume(ArithmeticException.class, exception -> arithmeticFallback())
//                .onErrorResume(exception -> defaultFallback()) // fallback en caso de excepcion
//                .onErrorReturn(-5) // lo mejor es siempre devolver un valor por defecto por si el fallback falla
//                .subscribe(Util.subscriber("mono_error_test"));

        onErrorContinue();
    }

    // lo usamos pa cuando ocurra un error manejarlo y continuar trabajando
    private static void onErrorContinue() {
        Flux.range(10, 10)
                .map(i -> i == 15 ? 5/0 : i)
                .onErrorContinue((exception, integer) -> {
                    log.error("oh no hay un error causado por este mk numero {}", integer);
                }).subscribe(Util.subscriber("onErrorContinue"));
    }

    // para cuando no nos interesa devolver ningun valor en el fallback, le decimos al subscriber
    // que todo bien y que ya tolis
    private static void onErrorComplete() {
        Flux.range(1, 10)
                .map(integer -> {
                    if (integer == 5) throw new RuntimeException();
                    return integer;
                })
                .onErrorComplete()
                .subscribe(Util.subscriber("onErrorComplete"));
    }

    // para cuando se quiera regresar un valor por defecto
    private static void fallbackValue() {
        Flux.range(1, 10)
                //.map(i -> i == 5 ? 5/0 : i)
                .map(i -> {
                    if (i == 4) throw new IllegalArgumentException();
                    return i;
                })
                .onErrorReturn(ArithmeticException.class, -1)
                .onErrorReturn(IllegalArgumentException.class, -2)
                .onErrorReturn(-3)
                .subscribe(Util.subscriber("error_test"));
    }

    private static Mono<Integer> arithmeticFallback() {
        int val = Util.getFaker().random().nextInt(1, 100);
        log.info("arithmetic 1 random value: {}", val);
        return Mono.just(val);
    }

    private static Mono<Integer> defaultFallback() {
//        int val = Util.getFaker().random().nextInt(1, 100);
//        log.info("default 2 random value: {}", val);
        //return Mono.just(val);
        return Mono.error(new ArithmeticException());
    }
}
