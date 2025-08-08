package sec10.assignment.groupBy;

import common.Util;

public record PurchaseOrder(String item,
                            String category,
                            Integer price) {

    public static PurchaseOrder create() {
        return new PurchaseOrder(
                Util.getFaker().commerce().productName(),
                Util.getFaker().commerce().department(),
                Util.getFaker().random().nextInt(20, 200)
        );
    }
}
