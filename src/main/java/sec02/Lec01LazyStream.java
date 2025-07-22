package sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class Lec01LazyStream {
    private static final Logger log = LoggerFactory.getLogger(Lec01LazyStream.class);

    public static void main(String[] args) {
        // por defecto stream es perezoso y no va a tirar una respuesta hasta
        // que no se la pida un to list o un to array
        Stream.of(1)
                .peek(i -> log.info("received: {}", i))
                .toList();
    }
}
