package sec13.client;

import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RateLimiter {
    private static final Map<String, Integer> categoryAttempts = Collections.synchronizedMap(new HashMap<>());

    static {
        categoryAttempts.put("standard", 2);
        categoryAttempts.put("prime", 3);
    }

    static <T> Mono<T> limitCalls() {
        return Mono.deferContextual(contextView -> {
            var allowCall = contextView.<String>getOrEmpty("category")
                    .map(RateLimiter::canAllow)
                    .orElse(false);

            return allowCall ? Mono.empty() : Mono.error(new RuntimeException("exceeded the given limit"));
        });
    }

    private static boolean canAllow(String category) {
        var attempts = categoryAttempts.getOrDefault(category, 0);

        if (attempts > 0) {
            categoryAttempts.put(category, attempts - 1);
            return true;
        }

        return false;
    }
}
