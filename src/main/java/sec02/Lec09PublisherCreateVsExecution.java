package sec02;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec09PublisherCreateVsExecution {
    private static final Logger log = LoggerFactory.getLogger(Lec09PublisherCreateVsExecution.class);

    public static void main(String[] args) {
        // cuando ejecutamos get name se crea el publisher pero no se ejecuta :)
        getName()
                .subscribe(Util.subscriber("Vivo"));
    }

    private static Mono<String> getName() {
        log.info("entering the method");

        return Mono.fromSupplier(() -> {
            log.info("generating name");
            Util.sleepSeconds(3);
            return Util.getFaker().name().firstName();
        });
    }
}
