package sec10.assignment.buffer;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class BookOrderService {
    public static Flux<BookOrder> getBookOrders() {
        return Flux.interval(Duration.ofMillis(200))
                .map(i -> BookOrder.create());
    }
}
