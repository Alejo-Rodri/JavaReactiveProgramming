package sec03;

import common.Util;
import reactor.core.publisher.Flux;

import java.util.List;

public class Lec03FluxFromIterableOrArray {
    public static void main(String[] args) {
        var list = List.of('a', 'b', 'c');

        Flux.just(list).subscribe(Util.subscriber("flux_just"));
        Flux.fromIterable(list).subscribe(Util.subscriber("fromIterable"));

        Integer[] arr = {1, 2, 3};
        Flux.fromArray(arr).subscribe(Util.subscriber("fromArray"));
    }
}
