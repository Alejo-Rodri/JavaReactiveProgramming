package sec03;

import common.Util;
import reactor.core.publisher.Flux;

public class Lec05FluxRange {
    public static void main(String[] args) {
        // empieza a contar desde 3 hasta sumar 10 elementos en este caso
        Flux.range(3, 10).subscribe(Util.subscriber());

        Flux
                .range(1, 10)
                .map(i -> Util.getFaker().name().firstName())
                .subscribe(Util.subscriber("range"));
    }
}
