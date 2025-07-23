package sec04;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import sec01.subscriber.SubscriberImpl;

public class Lec04FluxCreateDownstreamDemand {
    private static final Logger log = LoggerFactory.getLogger(Lec04FluxCreateDownstreamDemand.class);

    public static void main(String[] args) {
        // por defecto el flux create es eager y se van a hacer los calculos y las computaciones
        // antes del primer subscriptor y request
        produceEarly();

        // el comportamiento por defecto eager puede ser util pero como siempre depende
        // si se quiere poner lazy se puede utilizar fluxSink.onRequest() el cual como se espera
        // no hace nada hasta que se haga la peticion
        produceOnDemand();
    }

    public static void produceEarly() {
        var subscriber = new SubscriberImpl();

        Flux.<String>create(fluxSink -> {
            String name;
            for (int i = 0; i < 5; i++) {
                name = Util.getFaker().name().firstName();
                log.info("generated: {} early", name);
                fluxSink.next(name);
            }
            fluxSink.complete();
        }).subscribe(subscriber);

        subscriber.getSubscription().request(5);
    }

    public static void produceOnDemand() {
        var subscriber = new SubscriberImpl();

        Flux.<String>create(fluxSink -> {
            fluxSink.onRequest(request -> {
                String name;

                // si no se verifica que no este cancelado igual los va a generar aunque nadie los reciba
                for (int i = 0; i < request && !fluxSink.isCancelled(); i++) {
                    name = Util.getFaker().name().firstName();
                    log.info("generated {}th: {} on demand", i, name);
                    fluxSink.next(name);
                }

                // hay que tener mucho cuidado con el .complete() ya que despues de llamarlo esto termina
                // el flujo de datos de forma definitiva, y el subscriptor ya no puede llamar a cancel
                //fluxSink.complete();
            });
            // aca no sirve el onComplete porque sigue el codigo lo manda de una vez
            // al onRequest solo se entra cuando el subscriber hace una peticion
            //fluxSink.complete();
        }).subscribe(subscriber);

        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);
        subscriber.getSubscription().cancel();
        subscriber.getSubscription().request(2);
    }
}
