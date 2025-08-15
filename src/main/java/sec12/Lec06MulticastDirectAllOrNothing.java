package sec12;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class Lec06MulticastDirectAllOrNothing {
    private static final Logger log = LoggerFactory.getLogger(Lec06MulticastDirectAllOrNothing.class);

    public static void main(String[] args) {
        demo1();

        Util.sleepSeconds(5);
    }

    private static void demo1() {
        System.setProperty("reactor.bufferSize.small", "16");
        // directAllOrNothing - if one subscriber is slow do NOT deliver to anyone
        var sink = Sinks.many().multicast().directAllOrNothing();
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("fast_sub"));
        flux
                // this one that we know is slow buffers the data so it can receive it
                //.onBackpressureBuffer()
                .delayElements(Duration.ofMillis(200))
                .subscribe(Util.subscriber("slow_sub"));

        for (int i = 0; i < 100; i++) {
            Sinks.EmitResult result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }
    }
}
