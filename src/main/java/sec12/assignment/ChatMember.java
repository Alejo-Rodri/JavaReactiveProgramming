package sec12.assignment;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public interface ChatMember {
    void says(String message);
    void setFlux(Sinks.Many<Object> sinks);
}
