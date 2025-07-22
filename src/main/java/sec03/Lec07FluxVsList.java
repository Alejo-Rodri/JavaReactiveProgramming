package sec03;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sec01.subscriber.SubscriberImpl;
import sec03.helper.NameGenerator;

public class Lec07FluxVsList {
    private static final Logger log = LoggerFactory.getLogger(Lec07FluxVsList.class);

    public static void main(String[] args) {
        var list = NameGenerator.getNamesList(10);
        log.info(list.toString());

        NameGenerator.getNamesFlux(10).subscribe(Util.subscriber("flux_approach"));

        var subscriber = new SubscriberImpl();
        NameGenerator.getNamesFlux(10).subscribe(subscriber);
        subscriber.getSubscription().request(3);

    }
}
