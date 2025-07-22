package sec03.assignment;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sec03.client.ExternalServiceClient;

public class StockService {
    private static final Logger log = LoggerFactory.getLogger(StockService.class);

    public static int trade() {
        log.info("starting trade");
        var client = new ExternalServiceClient();
        var subscriber = new StockSubscriber<Integer>();
        var fluxStock = client.getStreamStock();

        fluxStock.subscribe(subscriber);
        //fluxStock.subscribe(Util.subscriber("fake"));

        return subscriber.getProfit();
    }
}
