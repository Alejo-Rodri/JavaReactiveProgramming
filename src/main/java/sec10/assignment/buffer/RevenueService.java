package sec10.assignment.buffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;

public class RevenueService {
    private static final Logger log = LoggerFactory.getLogger(RevenueService.class);
    private int acum;

    public Flux<Integer> revenueService(String[] genres) {
        return BookOrderService.getBookOrders()
                .buffer(Duration.ofSeconds(5))
                .map(bookOrders -> {
                    for (BookOrder bookOrder : bookOrders)
                        if (Arrays.stream(genres).anyMatch(
                                genre -> bookOrder.genre().equals(genre))
                        ) {
                            acum += bookOrder.price();
                            log.info("adding revenue from: {}", bookOrder);
                        }

                    return acum;
                })
                .cast(Integer.class);
    }
}
