package sec05;

import common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec04Delay {
    public static void main(String[] args) {
        // hace que cada elemento se publique en un intervalo de tiempo especifico
        // no pide todos los elementos a la vez, mas bien va pidendo de uno en uno cuando el intervalo se cumple
        Flux.range(2, 5)
                .log()
                .delayElements(Duration.ofSeconds(1))
                .subscribe(Util.subscriber("delay"));

        Util.sleepSeconds(10);
    }
}
