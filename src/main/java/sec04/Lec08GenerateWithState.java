package sec04;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec08GenerateWithState {
    private static final Logger log = LoggerFactory.getLogger(Lec08GenerateWithState.class);

    public static void main(String[] args) {
        // cuando el producer necesita la habilidad de detenerse, de cancelar?, de emitir error
        // de alguna manera necesita mantener un estado entre las llamadas al metodo generate
        // por ejemplo al inicio de la funcion lambda hacemos una conexion a una base de datos
        // estaria mal hacer esto cada vez que se llame al metodo, para esto se necesita un estado
        Flux.<String, Integer>generate(
                () -> 0,
                (counter, asynchronousSink) -> {
                    String name = Util.getFaker().country().name();
                    asynchronousSink.next(name);
                    counter++;
                    log.info("{}th: {}", counter, name);

                    if (counter == 10 || name.equalsIgnoreCase("canada")) asynchronousSink.complete();
                    return counter;
                }
        ).subscribe(Util.subscriber("generate state"));

        /*
        Flux.generate(
            () -> new SomeResource(), // stateSupplier
            (state, sink) -> {
                // lógica para emitir valores
                return state;
            },
            state -> {
                // lógica para liberar el recurso
                state.close();
            }
        );
         */
    }
}
