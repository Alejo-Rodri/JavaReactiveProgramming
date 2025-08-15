package sec12;

import common.Util;
import reactor.core.publisher.Sinks;

public class Lec04SinkMulticast {
    public static void main(String[] args) {
        demo2();
    }

    private static void demo1() {
        // onBackpressureBuffer - bounded queue
        // what's the purpose of the queue if not for emit the old values to the new subscribers?
        var sink = Sinks.many().multicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));

        sink.tryEmitNext("some");
        sink.tryEmitNext("sum");
        sink.tryEmitNext("same");

        Util.sleepSeconds(2);

        flux.subscribe(Util.subscriber("sub3"));
        sink.tryEmitNext("nuprublm");
    }

    // warmup
    private static void demo2() {
        // onBackpressureBuffer - bounded queue
        // what's the purpose of the queue if not for emit the old values to the new subscribers?
        // it is to store them only when we do not have ANY subscriber
        // only the FIRST subscriber receives the items in the queue
        var sink = Sinks.many().multicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        sink.tryEmitNext("some");
        sink.tryEmitNext("sum");
        sink.tryEmitNext("same");

        Util.sleepSeconds(2);

        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));
        flux.subscribe(Util.subscriber("sub3"));
        sink.tryEmitNext("nuprublm");
    }
}
