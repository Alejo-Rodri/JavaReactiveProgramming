package sec09;

import common.Util;
import reactor.core.publisher.Flux;
import sec09.assignment.ExternalServiceClient;

public class Lec12FlatMapAssignment {
    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        // todo se hace en un solo flux
        Flux.range(1, 10)
                .flatMap(client::getProduct)
                .transform(Util.fluxLogger("flatMapAssignment"))
                .subscribe(Util.subscriber("flatMapAssignment"));

        Util.sleepSeconds(5);
    }
}
