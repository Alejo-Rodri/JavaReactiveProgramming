package sec12;

import common.Util;
import reactor.core.publisher.Sinks;

public class Lec02SinkUnicast {
    public static void main(String[] args) {
        demo1();
    }

    private static void demo1() {
        // handle through which we would push items
        // onBackpressureBuffer - unbounded queue
        var sink = Sinks.many().unicast().onBackpressureBuffer();

        // handle through which subscribers will receive items
        var flux = sink.asFlux();

        sink.tryEmitNext("some");
        sink.tryEmitNext("sum");
        sink.tryEmitNext("same");
        sink.tryEmitComplete();

        flux.subscribe(Util.subscriber("unicast_sink"));
    }
}
