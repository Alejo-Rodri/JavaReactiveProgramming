package sec10;

import common.Util;
import sec10.assignment.window.LogService;

public class Lec04WindowAssignment {
    public static void main(String[] args) {
        LogService.processData()
                .subscribe();

        Util.sleepSeconds(20);
    }
}
