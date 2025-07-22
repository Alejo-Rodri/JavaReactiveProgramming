package main;

import sec01.publisher.PublisherImpl;
import sec01.subscriber.SubscriberImpl;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        demo3();
    }

    private static void demo1() {
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();

        publisher.subscribe(subscriber);
        var subscription = subscriber.getSubscription();
        subscription.request(5);
    }

    private static void demo2() throws InterruptedException {
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();

        publisher.subscribe(subscriber);
        var subscription = subscriber.getSubscription();
        subscription.request(2);
        Thread.sleep(Duration.ofSeconds(2));
        subscription.request(2);
        Thread.sleep(Duration.ofSeconds(2));
        subscription.cancel();
        subscription.request(2);
        Thread.sleep(Duration.ofSeconds(2));
    }

    private static void demo3() {
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();

        publisher.subscribe(subscriber);
        var subscription = subscriber.getSubscription();
        subscription.request(15);
    }
}