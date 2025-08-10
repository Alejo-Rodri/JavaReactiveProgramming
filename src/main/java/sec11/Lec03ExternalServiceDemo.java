package sec11;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.util.retry.Retry;
import sec11.client.ExternalServiceClient;
import sec11.client.ServerError;

import java.time.Duration;

public class Lec03ExternalServiceDemo {
    private static final Logger log = LoggerFactory.getLogger(Lec03ExternalServiceDemo.class);
    public static void main(String[] args) {
        var client = new ExternalServiceClient();
        //repeat(client);
        retry(client, 2);

        Util.sleepSeconds(20);
    }

    public static void repeat(ExternalServiceClient client) {
        client.getCountry()
                .repeat()
                .takeUntil(c -> c.equalsIgnoreCase("canada"))
                .subscribe(Util.subscriber("repeat"));
    }

    public static void retry(ExternalServiceClient client, int id) {
        client.getProductName(id)
                .retryWhen(
                        Retry.fixedDelay(5, Duration.ofSeconds(2))
                                .filter(err -> ServerError.class.equals(err.getClass()))
                                .doBeforeRetry(retrySignal -> log.info("retrying " + retrySignal.toString()))
                )
                .subscribe(Util.subscriber("repeat"));
    }
}
