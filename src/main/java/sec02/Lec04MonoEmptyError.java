package sec02;

import common.Util;
import reactor.core.publisher.Mono;

public class Lec04MonoEmptyError {
    public static void main(String[] args) {
        getUsername(1).subscribe(Util.subscriber());
        getUsername(2).subscribe(Util.subscriber());
        getUsername(3).subscribe(Util.subscriber());

        // aca le proveemos la implementacion para onNext, para cuando se le pasa algo
        // sin embargo no le decimos que hacer si le devolvemos un error, por esto, al recibir un error
        // no lo maneja y tira el default onErrorDropped
        getUsername(5).subscribe(
                s -> System.out.println(s)//,
                //err -> {}
        );
    }

    private static Mono<String> getUsername(int userId) {
        return switch (userId) {
            case 1 -> Mono.just("Pide que no se muera");
            case 2 ->  Mono.empty(); // es como devolver un optional.empty(), es mejor que regresar un null
            default -> Mono.error(new RuntimeException("invalid input"));
        };
    }
}
