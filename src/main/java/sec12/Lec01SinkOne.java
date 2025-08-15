package sec12;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class Lec01SinkOne {
    private static final Logger log = LoggerFactory.getLogger(Lec01SinkOne.class);

    public static void main(String[] args) {
        demo3();
    }

    private static void demo1() {
        // solo emite un elemento como mono
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();
        mono.subscribe(Util.subscriber("sink_one"));
//        sink.tryEmitValue("hi");
//        sink.tryEmitEmpty();
        sink.tryEmitError(new RuntimeException("upsi"));
    }

    private static void demo2() {
        // sink acts as a hot publisher
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();
        mono.subscribe(Util.subscriber("sam"));
        mono.subscribe(Util.subscriber("pepe"));
        sink.tryEmitValue("hi");
    }

    private static void demo3() {
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();

        mono.subscribe(Util.subscriber("sam"));

        // emit failure handler
        sink.emitValue("hi", ((signalType, emitResult) -> {
            log.info("hi");
            log.info(signalType.name());
            log.info(emitResult.name());
            return false;
        }));

        // falla porque es sink.one
        sink.emitValue("hello", ((signalType, emitResult) -> {
            log.info("hello");
            log.info(signalType.name());
            log.info(emitResult.name());
            return false;
        }));
    }
}
