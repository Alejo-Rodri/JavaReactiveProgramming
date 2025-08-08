package sec10;

import common.Util;
import sec10.assignment.groupBy.PurchaseOrderService;

public class Lec06GroupedFluxAssignment {
    public static void main(String[] args) {
        PurchaseOrderService.emitPurchaseOrders()
                .subscribe(Util.subscriber("groupedFluxAssignment"));

        Util.sleepSeconds(20);
    }
}
