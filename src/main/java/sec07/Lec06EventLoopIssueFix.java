package sec07;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.scheduler.Schedulers;
import sec07.client.ExternalServiceClient;

public class Lec06EventLoopIssueFix {
    private static final Logger log = LoggerFactory.getLogger(Lec06EventLoopIssueFix.class);

    public static void main(String[] args) {
        System.setProperty("reactor.schedulers.defaultBoundedElasticOnVirtualThreads", "true");
        var client = new ExternalServiceClient();

        for (int i = 0; i < 5; i++) {
            client.getProductName(i)
                    .publishOn(Schedulers.boundedElastic())
                    .map(Lec06EventLoopIssueFix::sleep)
                    .subscribe(Util.subscriber("non_blocking_thread"));
        }

        Util.sleepSeconds(10);
    }

    private static String sleep(String oleoDeMujerConSombrero) {
        Util.sleepSeconds(1);
        return oleoDeMujerConSombrero + "-processed.";
    }
}
