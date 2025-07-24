package sec05;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Lec09Timeout {
    private static final Logger log = LoggerFactory.getLogger(Lec09Timeout.class);
    public static void main(String[] args) {
        getProductName()
                // si el del fallback tambien se demora no lanza excepcion
                //.timeout(Duration.ofSeconds(1), fallback())
                .timeout(Duration.ofSeconds(1))
                // entonces? encadeno fallback tras fallback hasta que al final sea un valor por defecto?
                // asi tenga el tiempo aceptable llamara al timeout, sera que solo puede tener un timeout en el pipe?
                .onErrorResume(exception -> fallback().timeout(Duration.ofSeconds(1)))
                // es recomendable hacerlo asi si se necesita un timeout para el fallback
                .onErrorReturn("fallback porque muy lento :(")
                .subscribe(Util.subscriber("timeout"));

        Util.sleepSeconds(3);
    }

    private static Mono<String> getProductName() {
        return Mono.fromSupplier(() -> "service-" + Util.getFaker().commerce().productName())
                .delayElement(Duration.ofMillis(1800));
    }

    private static Mono<String> fallback() {
        return Mono.fromSupplier(() -> "fallback-" + Util.getFaker().commerce().productName())
                .delayElement(Duration.ofMillis(800));
    }
}
