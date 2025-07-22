package sec04;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import sec01.subscriber.SubscriberImpl;

public class Lec04FluxCreateDownstreamDemand {
    private static final Logger log = LoggerFactory.getLogger(Lec04FluxCreateDownstreamDemand.class);

    public static void main(String[] args) {
        var subscriber = new SubscriberImpl();

        Flux.<String>create(fluxSink -> {
            for (int i = 0; i < 5; i++) {
                String name;
                name = Util.getFaker().name().firstName();
                log.info("generated: {}", name);
                fluxSink.next(name);
            }
            fluxSink.complete();
        }).subscribe(subscriber);

        subscriber.getSubscription().request(5);
    }
}
