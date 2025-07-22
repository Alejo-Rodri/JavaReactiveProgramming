package sec03;

import common.Util;
import reactor.core.publisher.Flux;

public class Lec06Log {
    public static void main(String[] args) {
        Flux.range(1, 5)
                .log()
                .subscribe(Util.subscriber("log"));
    }
}
