package de.acando.jk.reactivedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	Mono<Void> createSomething(@RequestBody Mono<Person> body) {
		Mono<Void> result = repository.savePerson(body);
		/*
		 * everything done here is executed before the save, because the server triggers the execution
		 */
		System.out.println("Moep");
		return result;
	}

	// @GetMapping(value = "/persons", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@GetMapping(value = "/persons")
	Flux<Person> allPersons() {
		return repository.allPeople();
	}

}
