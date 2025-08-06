package sec09;

import common.Util;
import sec09.applications.OrderService;
import sec09.applications.UserService;

/*
    cuando estamos trabajando con mono que hacer si el resultado del flatmap es mas de un elemento
 */
public class Lec10MonoFlatMapMany {
    public static void main(String[] args) {
        UserService.getUserId("sam")
                // convierte el mono a un flux
                .flatMapMany(OrderService::getUserOrders)
                .subscribe(Util.subscriber("flatMapMany"));

        Util.sleepSeconds(3);
    }
}
