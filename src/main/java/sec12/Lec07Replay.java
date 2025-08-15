package sec12;

import common.Util;
import reactor.core.publisher.Sinks;

public class Lec07Replay {
    public static void main(String[] args) {
        demo1();
    }

    private static void demo1() {
        var sink = Sinks.many()
                .replay()
                .limit(2);  // store just the amount specified
                //.all();   // try to store all the data

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
}
