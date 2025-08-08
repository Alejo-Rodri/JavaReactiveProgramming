package sec10;

import common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Lec03Window {
    public static void main(String[] args) {
        eventStream()
                // .window(5)   // n elements
                .window(Duration.ofMillis(500))
                .flatMap(Lec03Window::processEvents)
                .subscribe();

        Util.sleepSeconds(30);
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(200))
                .concatWith(Flux.never())
                .map(i -> "event-" + i);
    }

    private static Mono<Void> processEvents(Flux<String> flux) {
        return flux.doOnNext(string -> System.out.print("*"))
                .doOnComplete(System.out::println)
                .then();
    }
}
