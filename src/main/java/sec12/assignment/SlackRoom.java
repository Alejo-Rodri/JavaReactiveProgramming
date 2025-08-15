package sec12.assignment;

import reactor.core.publisher.Sinks;

public class SlackRoom implements ChatRoom{
    private final Sinks.Many<Object> sinks;

    public SlackRoom(String name) {
        this.sinks = Sinks.many().replay().all();
    }

    @Override
    public void addMember(ChatMember chatMember) {
        chatMember.setFlux(sinks);
    }
}
