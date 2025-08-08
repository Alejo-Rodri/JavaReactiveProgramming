package sec10;

import common.Util;
import sec10.assignment.buffer.RevenueService;

public class Lec02BufferAssignment {
    public static void main(String[] args) {
        var revenueService = new RevenueService();

        revenueService.revenueService(new String[]{
                        "Science fiction",
                        "Fantasy",
                        "Suspense/Thriller"
                })
                .subscribe(Util.subscriber("buffer_assignment"));

        Util.sleepSeconds(20);
    }
}
