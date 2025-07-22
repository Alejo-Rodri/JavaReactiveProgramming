package sec03;

import common.Util;
import reactor.core.publisher.Flux;

public class Lec10FluxEmptyError {
    public static void main(String[] args) {
        // se usa cuando no hay nada pa pasarle al subscriptor
        Flux.empty().subscribe(Util.subscriber("empty"));
        // se usa para pasarle el error al subscriptor
        Flux.error(new RuntimeException("opsi")).subscribe(Util.subscriber("error"));
    }
}
