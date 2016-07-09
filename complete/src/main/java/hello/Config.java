package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.Environment;
import reactor.bus.EventBus;

import java.util.concurrent.CountDownLatch;

@Configuration
public class Config {

    @Value("${countdown.quotes.number}")
    public int numberOfQuotes;

    @Bean
    IPublisher getPublisher(EventBus eventBus, CountDownLatch countdownlatch){
        return new Publisher(eventBus,countdownlatch);
    }

    @Bean
    IReceiver getReciever(CountDownLatch countdownlatch){
        return new Receiver(countdownlatch);
    }

    @Bean
    Environment env() {
        return Environment.initializeIfEmpty()
                .assignErrorJournal();
    }

    @Bean
    public CountDownLatch latch() {
        return new CountDownLatch(numberOfQuotes);
    }

    @Bean
    EventBus createEventBus(Environment env) {
        return EventBus.create(env, Environment.THREAD_POOL);
    }

}
