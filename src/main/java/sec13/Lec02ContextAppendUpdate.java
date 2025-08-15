package sec13;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class Lec02ContextAppendUpdate {
    private static final Logger log = LoggerFactory.getLogger(Lec02ContextAppendUpdate.class);

    public static void main(String[] args) {
        getWelcomeMessage()
                .contextWrite(context -> context.delete("c"))
                .contextWrite(Context.of("a", "b").put("c", "d").put("e", "f"))
                .contextWrite(Context.of("user", "alejo"))
                .subscribe(Util.subscriber("context"));
    }

    // cleans the context
    private static void clean() {
        getWelcomeMessage()
                .contextWrite(Context.of("a", "b").put("c", "d").put("e", "f"))
                .contextWrite(context -> Context.empty())
                .contextWrite(Context.of("user", "alejo"))
                .subscribe(Util.subscriber("context"));
    }

    private static void append() {
        getWelcomeMessage()
                .contextWrite(Context.of("a", "b").put("c", "d").put("e", "f"))
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
