package sec12.assignment;

import common.Util;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class SlackMember implements ChatMember {
    //    private Flux<Object> flux;
    private Sinks.Many<Object> sinks;
    private final Subscriber<Object> subscriber;

    public SlackMember(String name) {
        this.subscriber = Util.subscriber(name);
    }

    @Override
    public void says(String message) {
        sinks.tryEmitNext(message);
    }

    @Override
    public void setFlux(Sinks.Many<Object> sinks) {
        this.sinks = sinks;
        sinks.asFlux().subscribe(subscriber);
    }
}
