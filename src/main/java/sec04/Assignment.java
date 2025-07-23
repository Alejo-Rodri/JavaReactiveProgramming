package sec04;

import common.Util;
import sec01.subscriber.SubscriberImpl;
import sec04.assignment.FileReaderServiceImpl;

import java.nio.file.Path;

public class Assignment {
    public static void main(String[] args) {
        var assignment = new FileReaderServiceImpl();

        var subscriptor = new SubscriberImpl();

        assignment.read(Path.of("src/main/resources/sec02/suavemente.txt"))
                .subscribe(subscriptor);

        subscriptor.getSubscription().request(3);
        Util.sleepSeconds(2);
        subscriptor.getSubscription().request(2);
        Util.sleepSeconds(2);
        subscriptor.getSubscription().cancel();
        subscriptor.getSubscription().request(2);
    }
}
