package sec09;

import common.Util;
import reactor.core.publisher.Flux;

/*
    To collect the items received via Flux. Assuming we will have finite items.
 */
public class Lec14CollectList {
    public static void main(String[] args) {

        // convierte flux en un mono que tiene la lista de numeros
        Flux.range(5, 10)
                .collectList()
                .subscribe(Util.subscriber("collectList"));
    }
}
