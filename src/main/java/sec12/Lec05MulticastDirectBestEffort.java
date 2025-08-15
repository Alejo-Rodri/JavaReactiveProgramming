package sec12;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class Lec05MulticastDirectBestEffort {
    private static final Logger log = LoggerFactory.getLogger(Lec05MulticastDirectBestEffort.class);

    public static void main(String[] args) {
        demo2();

        Util.sleepSeconds(10);
    }

    private static void demo1() {
        System.setProperty("reactor.bufferSize.small", "16");
        var sink = Sinks.many().multicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("fast_sub"));
        flux
                // when a subscriber is slow it can affect the delivery from sink of the rest of subs
                .delayElements(Duration.ofMillis(200))
                .subscribe(Util.subscriber("slow_sub"));

        for (int i = 0; i < 100; i++) {
            Sinks.EmitResult result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }
    }

    private static void demo2() {
        System.setProperty("reactor.bufferSize.small", "16");
        // directBestEffort - tells the publisher to focus in delivering the data to just the fastest subscriber
        var sink = Sinks.many().multicast().directBestEffort();
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("fast_sub"));
        flux
                // this one that we know is slow buffers the data so it can receive it
                .onBackpressureBuffer()
                .delayElements(Duration.ofMillis(200))
                .subscribe(Util.subscriber("slow_sub"));

        for (int i = 0; i < 100; i++) {
            Sinks.EmitResult result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }
    }
}
