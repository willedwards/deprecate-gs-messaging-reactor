package hello;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.Reactor;
import reactor.event.Event;

@Service
public class Publisher {
    
    @Autowired
    Reactor reactor;
    
    @Autowired
    CountDownLatch latch;
    
    @Autowired
    Integer numberOfQuotes;
    
    public void publishQuotes() throws InterruptedException {
        long start = System.currentTimeMillis();
        
        AtomicInteger counter = new AtomicInteger(1);
        
        for (int i=0; i < numberOfQuotes; i++) {
            reactor.notify("quotes", Event.wrap(counter.getAndIncrement()));
        }

        latch.await();
        
        long elapsed = System.currentTimeMillis()-start;
        
        System.out.println("Elapsed time: " + elapsed + "ms");
        System.out.println("Average time per quote: " + elapsed/ numberOfQuotes + "ms");
    }

}
