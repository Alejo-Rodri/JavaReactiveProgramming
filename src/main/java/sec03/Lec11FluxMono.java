package sec03;

import common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec11FluxMono {
    public static void main(String[] args) {
        // para convertir Flux <---> Mono
        fluxToMono();
        monoToFlux();
    }

    private static void fluxToMono() {
        var flux = Flux.range(1, 10);
        Mono<Integer> mono = Mono.from(flux);
        mono.subscribe(Util.subscriber("mono"));
    }

    private static void monoToFlux() {
        var mono = getUsername(1);
        save(Flux.from(mono));
    }

    private static Mono<String> getUsername(int userId) {
        return switch (userId) {
            case 1 -> Mono.just("Pide que no se muera");
            case 2 -> Mono.empty();
            default -> Mono.error(new RuntimeException("invalid input"));
        };
    }

    private static void save(Flux<String> flux) {
        flux.subscribe(Util.subscriber("saver"));
    }
}
