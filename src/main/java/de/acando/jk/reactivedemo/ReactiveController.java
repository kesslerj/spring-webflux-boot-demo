package de.acando.jk.reactivedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.acando.jk.reactivedemo.persistence.Person;
import de.acando.jk.reactivedemo.persistence.PersonRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author Jonas Keßler (jonas.kessler@acando.de)
 */
@RestController
public class ReactiveController {

	@Autowired
	private PersonRepository repository;

	@PostMapping("/persons")
	Mono<Void> createPerson(@RequestBody Mono<Person> body) {
		System.out.println("POST /persons incoming");
		Mono<Void> result = repository.save(body);
		/*
		 * everything done here (not chained to the mono) is executed before the save, because the server triggers the execution
		 */
		System.out.println("POST /persons returning");
		return result;
	}

	// @GetMapping(value = "/persons", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@GetMapping(value = "/persons")
	Flux<Person> allPersons() {
		System.out.println("GET /persons incoming");
		Flux<Person> findAll = repository.findAll();
		System.out.println("GET /persons returning");
		return findAll;
	}

	@GetMapping(value = "/persons/{id}")
	Mono<Person> singlePerson(@PathVariable("id") int id) {
		System.out.println("GET /persons/{id} incoming");
		Mono<Person> result = repository.findById(Mono.just(id));
		System.out.println("GET /persons/{id} returning");
		return result;
	}

}
