package sec09.applications;

import common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class OrderService {
    private static final Map<Integer, List<Order>> orderTable = Map.of(
            1, List.of(
                    new Order(1, Util.getFaker().commerce().productName(), Util.getFaker().random().nextInt(10, 100)),
                    new Order(1, Util.getFaker().commerce().productName(), Util.getFaker().random().nextInt(10, 100))
            ),
            2, List.of(
                    new Order(2, Util.getFaker().commerce().productName(), Util.getFaker().random().nextInt(10, 100)),
                    new Order(2, Util.getFaker().commerce().productName(), Util.getFaker().random().nextInt(10, 100)),
                    new Order(2, Util.getFaker().commerce().productName(), Util.getFaker().random().nextInt(10, 100))
            ),
            3, List.of()
    );

    public static Flux<Order> getUserOrders(Integer userId) {
        return Flux.fromIterable(orderTable.get(userId))
                .delayElements(Duration.ofMillis(500))
                .transform(Util.fluxLogger("order_user_" + userId));
    }
}
