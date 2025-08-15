package sec13;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

public class Lec03ContextPropagation {
    private static final Logger log = LoggerFactory.getLogger(Lec03ContextPropagation.class);

    public static void main(String[] args) {
        getWelcomeMessage()
                // we can propagate the context information to all the upstream producers
                .concatWith(
                        Flux.merge(
                                producer1(),
                                // if we do not want the producer 2 to have context
                                producer2().contextWrite(ctx -> Context.empty())
                        )
                )
                .contextWrite(Context.of("user", "alejo"))
                .subscribe(Util.subscriber("context"));

        Util.sleepSeconds(2);
    }

    private static Mono<String> getWelcomeMessage() {
        return Mono.deferContextual(contextView -> {
            log.info("{}", contextView);

            if (contextView.hasKey("user")) {
                return Mono.just("Welcome %s".formatted(contextView.<String>get("user")));
            }
            return Mono.error(new RuntimeException("unauthenticated"));
        });
    }

    private static Mono<String> producer1() {
        return Mono.<String>deferContextual(ctx -> {
            log.info("producer1 ctx: {}", ctx);
            return Mono.empty();
        })
                .subscribeOn(Schedulers.boundedElastic());
    }

    private static Mono<String> producer2() {
        return Mono.<String>deferContextual(ctx -> {
                    log.info("producer2 ctx: {}", ctx);
                    return Mono.empty();
                })
                .subscribeOn(Schedulers.parallel());
    }
}
