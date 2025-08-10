package sec11;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/*
    retry resubscribes when receives an error signal
 */
public class Lec02Retry {
    private static final Logger log = LoggerFactory.getLogger(Lec02Retry.class);

    public static void main(String[] args) {
        demo3();

        Util.sleepSeconds(5);
    }

    public static void demo1() {
        getCountryName()
                .retry()
                .subscribe(Util.subscriber("retry"));
    }

    public static void demo2() {
        getCountryName()
                .retry(3)
                .subscribe(Util.subscriber("retry"));
    }

    public static void demo3() {
        getCountryName()
                .retryWhen(
                        Retry.fixedDelay(2, Duration.ofSeconds(1))
                                .doBeforeRetry(retrySignal -> log.info("retrying demo3 {}", retrySignal.toString()))
                )
                .subscribe(Util.subscriber("retry"));
    }

    public static void demo4() {
        getCountryName()
                .retryWhen(
                        Retry.fixedDelay(2, Duration.ofSeconds(1))
                                // retry if the exception is RuntimeException
                                // i.e. if the server returns 500 we should retry but if is 400 we shouldn't
                                .filter(throwable -> RuntimeException.class.equals(throwable.getClass()))
                                //.onRetryExhaustedThrow((spec, signal) -> signal.failure())
                )
                .subscribe(Util.subscriber("retry"));
    }

    private static Mono<String> getCountryName() {
        var atomicInt = new AtomicInteger();
        return Mono.fromSupplier(() -> {
            if (atomicInt.incrementAndGet() < 3)
                throw new RuntimeException("oops");

            return Util.getFaker().country().name();
        })
                .doOnError(err -> log.error("ERROR: ", err))
                .doOnSubscribe(s -> log.info("subscribing {}", s));

    }
}
