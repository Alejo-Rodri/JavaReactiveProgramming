package sec05;

import common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec07DefaultIfEmpty {
    public static void main(String[] args) {
        // retorna valor especifico metido al codigo, sabemos que esto generalmente no es bueno
        Mono.empty()
                .defaultIfEmpty("oh no es empty")
                .subscribe(Util.subscriber("mono_empty"));

        Flux.range(30, 10)
                .filter(integer -> integer == 0)
                .cast(String.class) // esto no tiene mucho sentido jjajajaja
                .defaultIfEmpty("nada pasa el filtro")
                .subscribe(Util.subscriber("flux_empty"));
    }
}
