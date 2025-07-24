package sec05;

import common.Util;
import reactor.core.publisher.Flux;

public class Lec01Handle {
    public static void main(String[] args) {
        // es como una especie de filter y map todo en uno
        // requerimientos
        // 1 => -2
        // 4 => don't send
        // 7 => error
        // everything else => send as it is
        Flux<Integer> flux = Flux.range(3, 10);


        // la creacion de objetos que ocurre con cada nuevo decorador no es una carga significante
        // para la memoria, estan optimizados para ser usados de esta forma y son livianos
        Flux<Integer> decorator = flux.handle((item, synchronousSink) -> {
            if (item == 1) synchronousSink.next(-2);
            else if (item == 4) {
                // nothing
            } else if (item == 7) synchronousSink.error(new RuntimeException("cant be 7 cos don't like that number"));
            else synchronousSink.next(item);
        }).cast(Integer.class);
        decorator.subscribe(Util.subscriber("handle"));
        // esto es muy util porque con filter y map por separado no se puede transformar un valor
        // en especifico en otra cosa, map transforma cada elemento y filter lo desecha
    }
}
