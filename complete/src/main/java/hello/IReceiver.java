package hello;

import reactor.bus.Event;
import reactor.fn.Consumer;

public interface IReceiver extends Consumer<Event<Integer>> {
}
