package sec04;

import common.Util;
import reactor.core.publisher.Flux;

public class Lec06FluxGenerator {
    public static void main(String[] args) {
        assignment();

        // synchronousSink solo puede emitir un valor, solo se puede llamar a onNext una vez
        // se llama a si mismo una y otra vez hasta que llame a complete o el subscriptor lo cancele
        // invoca la expresion lambda en bucle segun la demanda del subscriptor
        // se detiene si se llama complete, error o si el subscriptor cancela
        // por que?
        Flux.generate(synchronousSink -> {
                    synchronousSink.next(1);
                    //synchronousSink.next(2);
                    //synchronousSink.complete();
                })
                .take(4)
                .subscribe(Util.subscriber("generate"));
    }

    public static void assignment() {
        Flux.<String>generate(synchronousSink -> {
                    String name = Util.getFaker().country().name();
                    synchronousSink.next(name);
                })
                .takeUntil(s -> s.equalsIgnoreCase("canada"))
                .subscribe(Util.subscriber("assignment synchronous sink"));
    }
}
