package de.acando.jk.reactivedemo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;

import de.acando.jk.reactivedemo.persistence.Person;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ReactiveDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveDemoApplication.class, args);
	}

	@Bean
	ApplicationRunner onStartup(ReactiveMongoOperations mongoTemplate) {

		// We need to call ….block() here to actually execute the call.
		return (args) -> mongoTemplate.insert(Mono.just(new Person("Dave Matthews", 12))).block();
	}
}
