package sec13;

import common.Util;
import sec13.client.ExternalServiceClient;

public class Lec04ContextRateLimiterDemo {
    public static void main(String[] args) {
        var client = new ExternalServiceClient();
        client.getBook()
                .subscribe(Util.subscriber("context_rate"));

        Util.sleepSeconds(2);
    }
}
