package sec07;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec01DefaultBehaviourDemo {
    private static final Logger log = LoggerFactory.getLogger(Lec01DefaultBehaviourDemo.class);

    // el thread del que se subscribe hace todo el trabajo
    public static void main(String[] args) {
        var flux = Flux.create(fluxSink -> {
                    for (int i = 0; i < 3; i++) {
                        log.info("generating {}", i);
                        fluxSink.next(i);
                    }

                    fluxSink.complete();
                })
                .doOnNext(value -> log.info("value: {}", value));

        Runnable runnable = () -> flux.subscribe(Util.subscriber("sub1"));
        Thread.ofPlatform().start(runnable);
    }
}
