package sec03;

import common.Util;
import reactor.core.publisher.Flux;

public class Lec01FluxJust {
    public static void main(String[] args) {
        Flux.just(1, 2, 3, "max", false)
                .subscribe(Util.subscriber("just"));
    }
}
