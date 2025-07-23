package sec04;

import common.Util;
import reactor.core.publisher.Flux;

public class Lec05TakeOperator {
    public static void main(String[] args) {
        takeUntil();
    }

    private static void take() {
        // el operador take lo que hace es limitar la cantidad de datos que emite el publisher
        // es como el log en el sentido que take es un subscripor de range y un publisher de subscribe
        Flux.range(1, 10)
                .log("take")
                // subscribe hace el request de todos los datos que el publisher tenga para dar
                // take intercepta esto y solo pide 4, cuando le pasan los 4 al take cancela la subscripcion
                .take(4)
                .log("subscribe")
                .subscribe(Util.subscriber("take"));
    }

    private static void takeWhile() {
        Flux.range(1, 10)
                .log("takeWhile")
                .takeWhile(i -> i < 5) // emite cancel cuando no se cumple la condicion
                .log("subscribe")
                .subscribe(Util.subscriber("takeWhile"));
    }

    private static void takeUntil() {
        Flux.range(1, 10)
                .log("takeUntil")
                .takeUntil(i -> i > 5) // emite cancel cuando se cumple la condicion
                .log("subscribe")
                .subscribe(Util.subscriber("takeUntil"));
    }
}
