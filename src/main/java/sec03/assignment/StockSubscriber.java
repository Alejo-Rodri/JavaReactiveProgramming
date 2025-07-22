package sec03.assignment;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockSubscriber<T> implements Subscriber<T> {
    private static final Logger log = LoggerFactory.getLogger(StockSubscriber.class);
    private Subscription subscription;

    private int balance = 1000;
    private int profit = 0;
    private int stock = 0;

    public Subscription getSubscription() {
        return subscription;
    }

    public int getProfit() {
        return profit;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T price) {
        log.info(String.valueOf(price));
        int value = Integer.parseInt(String.valueOf(price));

        if (value < 90 && balance - value >= 0) {
            stock++;
            balance -= value;
            log.info("buying stock... balance: ${}", balance);
        } else if (value > 110) {
            log.info("selling stock...");
            profit += stock * value;
            subscription.cancel();
            log.info("final stock: {}\tfinal balance: ${}\tfinal profit: ${}", stock, balance, profit);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("error", throwable);
    }

    @Override
    public void onComplete() {
        log.info("completed!");
    }
}
