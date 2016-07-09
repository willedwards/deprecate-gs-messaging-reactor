package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import reactor.Environment;
import reactor.bus.EventBus;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static reactor.bus.selector.Selectors.$;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	public Config config;

	@Autowired
	private EventBus eventBus;

	@Autowired
	private IReceiver receiver;

	@Autowired
	private IPublisher publisher;

	@Override
	public void run(String... args) throws Exception {
		eventBus.on($("quotes"), receiver);
		publisher.publishQuotes(config.numberOfQuotes);
	}

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext app = SpringApplication.run(Application.class, args);

		app.getBean(CountDownLatch.class).await(1, TimeUnit.SECONDS);

		app.getBean(Environment.class).shutdown();
	}

}
