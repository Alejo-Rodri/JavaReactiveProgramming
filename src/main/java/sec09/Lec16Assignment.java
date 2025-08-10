package sec09;

import common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sec09.applications.*;

import java.util.List;

public class Lec16Assignment {
    record UserInformation(Integer userId,
                           String name,
                           Integer balance,
                           List<Order> orders) {}

    public static void main(String[] args) {
        UserService.getAllUsers()
                .flatMap(Lec16Assignment::getUserInformation)
                .subscribe(Util.subscriber("assignment"));

        Util.sleepSeconds(2);
    }

    private static Mono<UserInformation> getUserInformation(User user) {
        return Mono.zip(PaymentService.getUserBalance(user.id()),
                OrderService.getUserOrders(user.id()).collectList())
                .map(t -> new UserInformation(user.id(), user.username(), t.getT1(), t.getT2()));
    }
}
