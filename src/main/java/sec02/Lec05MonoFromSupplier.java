package sec02;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec05MonoFromSupplier {
    private static final Logger log = LoggerFactory.getLogger(Lec05MonoFromSupplier.class);

    public static void main(String[] args) {
        var list = List.of(1, 2, 3);

        // Mono.just() ejecutara la operacion aunque nadie este subscrito, esto es desperdicio de
        // procesamiento el cual no regalan, Mono.just() se usa cuando el dato que buscamos enviar ya
        // esta cargado en memoria
        Mono.just(sum(list));
                //.subscribe(Util.subscriber("Me veras volar por la ciudad de la furia"));
        // esto se puede solucionar con Mono.fromSupplier() el cual solo carga el valor si es que alguien se subscribe
        // solo realiza instrucciones si alguien se subscribe
        Mono.fromSupplier(() -> sum(list));
                //.subscribe();
    }

    private static int sum(List<Integer> list) {
        log.info("finding the sum of {}", list);
        return list.stream().mapToInt(a -> a).sum();
    }
}
