package sec13;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

//  provides metadata about the subscription
public class Lec01Context {
    private static final Logger log = LoggerFactory.getLogger(Lec01Context.class);

    public static void main(String[] args) {
        getWelcomeMessage()
                .contextWrite(Context.of("user", "alejo"))
                .subscribe(Util.subscriber("context"));
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
}
