package sec07;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec07PublishOnSubscribeOn {
    private static final Logger log = LoggerFactory.getLogger(Lec07PublishOnSubscribeOn.class);

    public static void main(String[] args) {
        Flux.range(30, 20)
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i -> log.info("received: {}", i))
                .doFirst(() -> log.info("vivo en un pais libre"))
                .subscribeOn(Schedulers.parallel())
                .doFirst(() -> log.info("que no es lo mismo pero es igual"))
                .publishOn(Schedulers.parallel())
                .doOnNext(i -> log.info("quiero que me perdonen los muertos de mi felicidad {}" , i))
                .subscribe(Util.subscriber("pub&sub"));

        Util.sleepSeconds(10);
    }
}
