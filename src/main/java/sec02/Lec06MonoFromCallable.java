package sec02;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec06MonoFromCallable {
    private static final Logger log = LoggerFactory.getLogger(Lec06MonoFromCallable.class);

    public static void main(String[] args) {
        var list = List.of(1, 2, 3);

        // aqui hay que envolver la excepcion en un try catch para que funcione
        //Mono.fromRunnable(sum(list));
        //.fromCallable igual es lazy solo que tiene la ventaja que maneja excepciones
        Mono.fromCallable(() -> sum(list)).subscribe(Util.subscriber("Donald"));
    }

    private static int sum(List<Integer> list) throws Exception {
        log.info("finding the sum of {}", list);
        return list.stream().mapToInt(a -> a).sum();
    }
}
