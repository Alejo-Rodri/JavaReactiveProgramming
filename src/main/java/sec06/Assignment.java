package sec06;

import common.Util;
import sec06.assignment.Client;
import sec06.assignment.InventoryService;
import sec06.assignment.RevenueService;

public class Assignment {
    public static void main(String[] args) {
        var client = new Client();
        var inventoryService = new InventoryService();
        var revenueService = new RevenueService();
        var flux = client.getProductStream()
                .transform(revenueService.addInventory())
                .transform(inventoryService.addInventory());
        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));
        flux.subscribe(Util.subscriber("sub3"));

        Util.sleepSeconds(20);
    }
}
