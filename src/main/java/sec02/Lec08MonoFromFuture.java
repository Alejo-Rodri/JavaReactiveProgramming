package sec02;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class Lec08MonoFromFuture {
    private static final Logger log = LoggerFactory.getLogger(Lec08MonoFromFuture.class);

    public static void main(String[] args) {

        // necesita uno que maneje futuros, para eso esta fromFuture
        //Mono.fromSupplier(getName());

        // fromFuture recibe completableFuture
        // de esta forma no es lazy
        Mono.fromFuture(getName());
                //.subscribe(Util.subscriber("future"));

        // de esta forma si es lazy, simplemente se envuelve el CompletableFuture en un Supplier
        Mono.fromFuture(() -> getName())
                        .subscribe(Util.subscriber("lazy future"));

        Util.sleepSeconds(1);
    }

    private static CompletableFuture<String> getName() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("generating name");
            return Util.getFaker().name().firstName();
        });
    }
}
