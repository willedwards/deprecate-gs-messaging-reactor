package hello;

import org.springframework.web.client.RestTemplate;
import reactor.bus.Event;
import reactor.fn.Consumer;

import java.util.concurrent.CountDownLatch;

class Receiver implements IReceiver {

	private final CountDownLatch latch;
	private final RestTemplate restTemplate = new RestTemplate();

	public Receiver(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void accept(Event<Integer> ev) {
		QuoteResource quoteResource = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", QuoteResource.class);
		System.out.println("Quote " + ev.getData() + ": " + quoteResource.getValue().getQuote());
		latch.countDown();
	}

}