package sec01.publisher;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriptionImpl implements Subscription {
    private static final Logger log = LoggerFactory.getLogger(SubscriptionImpl.class);
    private static final int MAX_ITEMS = 10;
    private final Faker faker;
    private final Subscriber<? super String> subscriber;
    private boolean isCancelled;
    private int count = 0;

    public SubscriptionImpl(Subscriber<? super String> subscriber) {
        this.subscriber = subscriber;
        this.faker = Faker.instance();
    }

    @Override
    public void request(long requested) {
        if (isCancelled) return;
        if (requested > MAX_ITEMS) {
            this.subscriber.onError(new RuntimeException("validation failed"));
            this.isCancelled = true;
            return;
        }

        log.info("subscriber has requested {} items", requested);
        for (int i = 0; i < requested && count < MAX_ITEMS; i++) {
            this.subscriber.onNext(this.faker.internet().emailAddress());
            count++;
        }

        if (count == MAX_ITEMS) {
            log.info("no more data to show");
            this.isCancelled = true;
            this.subscriber.onComplete();
        }
    }

    @Override
    public void cancel() {
        isCancelled = true;
        log.info("subscriber has cancelled!");
    }
}
