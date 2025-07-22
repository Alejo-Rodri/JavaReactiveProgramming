package sec04;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sec04.helper.NameGenerator;

import java.util.ArrayList;

public class Lec03FluxSinkThreadSafety {
    private static final Logger log = LoggerFactory.getLogger(Lec03FluxSinkThreadSafety.class);

    public static void main(String[] args) {
        demo1();
        demo2();
    }

    // not thread safe
    private static void demo1() {
        var list = new ArrayList<Integer>();
        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) list.add(i);
        };

        for (int i = 0; i < 10; i++) Thread.ofPlatform().start(runnable);

        Util.sleepSeconds(3);
        // siempre da un tamano distinto, no es thread safe
        //  debe poder ser modificado y accedido por multple hilos al mismo tiempo sin provocar errores,
        //  inconsistencias o comportamientos inesperados
        log.info("list size: {}", list.size());
    }

    // thread safe
    private static void demo2() {
        var list = new ArrayList<String>();
        var generator = new NameGenerator();
        var flux = Flux.create(generator, FluxSink.OverflowStrategy.BUFFER);
        // le estamos pasando un Consumer
        flux.subscribe(name -> list.add(name));

        Runnable runnable = () -> {
            for (int i = 0; i < 100000; i++) generator.generate();
        };

        for (int i = 0; i < 10; i++) Thread.ofPlatform().start(runnable);
        Util.sleepSeconds(3);

        log.info("list size: {}", list.size());
    }
}
