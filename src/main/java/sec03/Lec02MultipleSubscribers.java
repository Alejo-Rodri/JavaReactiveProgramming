package sec03;

import common.Util;
import reactor.core.publisher.Flux;

public class Lec02MultipleSubscribers {
    public static void main(String[] args) {
        var flux = Flux.just(1, 2, 3, 4, 5, 6);

        flux.subscribe(Util.subscriber("sub1"));
        flux
                .map(c -> c + "a")
                .subscribe(Util.subscriber("sub2"));
        // se pueden aplicar operaciones de filter, map, etc
        flux
                .filter(integer -> integer % 2 == 0)
                .subscribe(Util.subscriber("sub3"));
    }
}
