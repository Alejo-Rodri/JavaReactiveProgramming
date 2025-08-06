package sec09;

import common.Util;
import sec09.applications.PaymentService;
import sec09.applications.UserService;

/*
    Sequential non blocking IO calls
    flatMap is used to flatten the inner publisher to subscribe to the inner publisher
 */
public class Lec09MonoFlatMap {
    public static void main(String[] args) {
        /*
        We have username
        Get user account balance
         */
        UserService.getUserId("sam")
                .flatMap(PaymentService::getUserBalance)
                .subscribe(Util.subscriber("getUserBalance"));

        Util.sleepSeconds(3);
    }
}
