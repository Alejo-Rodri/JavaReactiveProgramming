package sec09;

import common.Util;
import reactor.core.publisher.Flux;
import sec09.assignment.ExternalServiceClient;

public class Lec13ConcatMap {
    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        Flux.range(1, 10)
                // los devuelve secuencialmente, como se tiene que asegurar que
                // lleguen en orden concatMap se vuelve bloqueante
                .concatMap(client::getProduct)
                .transform(Util.fluxLogger("concatMap"))
                .subscribe(Util.subscriber("flatMapAssignment"));

        Util.sleepSeconds(5);
    }
}
