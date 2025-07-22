package sec04;

import common.Util;
import reactor.core.publisher.Flux;

public class Lec01FluxCreate {
    public static void main(String[] args) {
        Flux.create(fluxSink -> {
            String country;
            do {
                country = Util.getFaker().country().name();
                fluxSink.next(country);
            } while (!country.equalsIgnoreCase("canada"));

            fluxSink.complete();
        })
                .subscribe(Util.subscriber("create"));
    }
}
