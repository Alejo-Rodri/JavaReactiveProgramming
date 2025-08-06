package sec09;

import common.Util;
import sec09.helper.Despegar;

public class Lec06MergeUseCase {
    public static void main(String[] args) {
        Despegar.getFLights()
                .subscribe(Util.subscriber("fool in love"));

        Util.sleepSeconds(3);
    }
}
