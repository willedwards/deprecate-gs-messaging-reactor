package hello;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import reactor.event.Event;
import reactor.function.Consumer;

@Service
class Receiver implements Consumer<Event<Integer>> {

    @Autowired
    CountDownLatch latch;
    
    RestTemplate restTemplate = new RestTemplate();

    public void accept(Event<Integer> ev) {
		QuoteResource quoteResource = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", QuoteResource.class);
        System.out.println("Quote " + ev.getData() + ": " + quoteResource.getValue().getQuote());
        latch.countDown();
    }

}