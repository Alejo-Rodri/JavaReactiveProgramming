package sec05;

import common.Util;
import reactor.core.publisher.Flux;

public class Lec08SwitchIfEmpty {
    public static void main(String[] args) {
        // por ejemplo queremos traer un nombre que puede estar en redis o en la db
        Flux.range(3, 3)
                // primero consulta redis
                .filter(i -> i > 7)
                // si no esta en la cache consulta la db como fallback
                .switchIfEmpty(switchIfEmpty())
                .subscribe(Util.subscriber("switch_empty"));
    }

    private static Flux<Integer> switchIfEmpty() {
        return Flux.range(100, 3);
    }
}
