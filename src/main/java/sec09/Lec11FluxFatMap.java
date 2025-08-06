package sec09;

import common.Util;
import sec09.applications.OrderService;
import sec09.applications.User;
import sec09.applications.UserService;

public class Lec11FluxFatMap {
    public static void main(String[] args) {
        /*
            Get all the orders from order service
         */

        UserService.getAllUsers()
                .map(User::id)
                .flatMap(OrderService::getUserOrders)
                .subscribe(Util.subscriber("flatMap"));

        Util.sleepSeconds(3);
    }
}
