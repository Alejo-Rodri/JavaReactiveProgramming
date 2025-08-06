package sec09;

import common.Util;
import sec09.assignment.ExternalServiceClient;

public class Lec08ZipAssignment {
    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        // nos subscribimos y creamos 10 Mono
        for (int i = 0; i < 10; i++) {
            client.getProduct(i)
                    .subscribe(Util.subscriber("zip assignment"));
        }

        Util.sleepSeconds(5);
    }
}
