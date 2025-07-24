package sec05;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec02HandleUntilAssignment {
    private static final Logger log = LoggerFactory.getLogger(Lec02HandleUntilAssignment.class);
    public static void main(String[] args) {
        assignment();
    }

    private static void assignment() {
        Flux.<String>generate(synchronousSink -> {
                    String name = Util.getFaker().country().name();
                    synchronousSink.next(name);
                })
                //.takeUntil(s -> s.equalsIgnoreCase("canada"))
                // es mejor hacerlo con el takeUntil
                .handle((name, synchronousSink) -> {
                    if (!name.equalsIgnoreCase("canada")) synchronousSink.next(name);
                    else {
                        log.info("found canada :D");
                        synchronousSink.complete();
                    }
                })
                .cast(String.class)
                .subscribe(Util.subscriber("assignment synchronous sink"));
    }
}
