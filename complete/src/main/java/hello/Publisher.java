package hello;

import reactor.bus.Event;
import reactor.bus.EventBus;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Publisher implements IPublisher {


	private final EventBus eventBus;
	private final CountDownLatch latch;

	public Publisher(EventBus eventBus, CountDownLatch latch) {
		this.eventBus = eventBus;
		this.latch = latch;
	}

	@Override
	public void publishQuotes(int numberOfQuotes) throws InterruptedException {
		long start = System.currentTimeMillis();

		AtomicInteger counter = new AtomicInteger(1);

		for (int i = 0; i < numberOfQuotes; i++) {
			eventBus.notify("quotes", Event.wrap(counter.getAndIncrement()));
		}

		latch.await();

		long elapsed = System.currentTimeMillis() - start;

		System.out.println("Elapsed time: " + elapsed + "ms");
		System.out.println("Average time per quote: " + elapsed / numberOfQuotes + "ms");
	}

}
