package sec04.helper;

import common.Util;
import reactor.core.publisher.FluxSink;

// que es esta interfaz Consumer?
// interfaz funcional usada para cambiar el estado de lo que se le pase
import java.util.function.Consumer;

public class NameGenerator implements Consumer<FluxSink<String>> {
    // que es el sink?
    // se usa para empujar los datos hacia el flux o mono usando codigo imperatimvo
    private FluxSink<String> sink;

    // si solamente se inicializa con el accept no se ve muy seguro, puede ser null?
    // si, hay que meterle carpinteria para manejar esto
    @Override
    public void accept(FluxSink<String> stringFluxSink) {
        this.sink = stringFluxSink;
    }

    public void generate() {
        if (sink == null) return;
        this.sink.next(Util.getFaker().name().firstName());
    }
}
