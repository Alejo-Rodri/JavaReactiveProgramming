package sec04;

import common.Util;
import reactor.core.publisher.Flux;
import sec04.helper.NameGenerator;

public class Lec02FluxCreateRefactor {
    public static void main(String[] args) {
        var generator = new NameGenerator();
        // le paso el generator que implementa la interfaz Consumer<Flux<String>>
        var flux = Flux.create(generator);
        // en que momento al Consumer se llama al accept?
        // accept se llama unicamente cuando alguien se subscribe al flux,
        // entonces es importante verificar que exista el sink antes de pensar en emitir datos
        flux.subscribe(Util.subscriber("generator"));

        for (int i = 0; i < 10; i++) {
            generator.generate();
        }
    }
}
