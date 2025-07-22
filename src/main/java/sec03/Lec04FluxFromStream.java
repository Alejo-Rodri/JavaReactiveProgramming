package sec03;

import common.Util;
import reactor.core.publisher.Flux;

import java.util.List;

public class Lec04FluxFromStream {
    public static void main(String[] args) {
        var list = List.of(1, 2, 3);
        var stream  = list.stream();

        // aca proveemos un Supplier de stream, entonces se genera un stream nuevo cada vez
        // que alguien se subscribe
        var streamSupplier = Flux.fromStream(() -> list.stream());
        streamSupplier.subscribe(Util.subscriber("sup1"));
        streamSupplier.subscribe(Util.subscriber("sup2"));

        // aca se trabaja con el mismo stream siempre y solo se puede operar una vez en el
        var flux = Flux.fromStream(stream);
        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));
    }
}
