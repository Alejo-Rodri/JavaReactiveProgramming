package sec05;

import common.Util;
import sec05.assignment.ProductServiceImpl;

public class Assignment {
    public static void main(String[] args) {
        var product = new ProductServiceImpl();
        for (int i = 0; i < 5; i++) {
            product.getProductName(i).subscribe(Util.subscriber("assignment"));
        }
        Util.sleepSeconds(10);
    }
}
