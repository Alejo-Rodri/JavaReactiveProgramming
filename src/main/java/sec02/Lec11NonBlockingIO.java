package sec02;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sec02.client.ExternalServiceClient;

public class Lec11NonBlockingIO {
    private static final Logger log = LoggerFactory.getLogger(Lec11NonBlockingIO.class);

    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        log.info("sending requests");

        // envia todas las peticiones en el mismo hilo, eso es bueno
        // son non-blocking, se envian practicamente al mismo tiempo y la respuesta sobre la red puede variar
        // si hubiera sido una peticion http comun se habria usado un hilo del thread pool por cada uno
        // y si solo puede usar un hilo se demoraria 1 * la cantidad de peticiones segundos
        // de esta forma todas las peticiones se hacen de forma no bloqueante y en el mismo hilo
        // ¿como funciona esto?
        for (int i = 0; i < 200; i++) {
            client.getProductName(i)
                    .subscribe(Util.subscriber("bunkers"));
        }

        Util.sleepSeconds(2);
    }
}
