package de.acando.jk.reactivedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.acando.jk.reactivedemo.persistence.Person;
import de.acando.jk.reactivedemo.persistence.PersonRepository;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 *
 * @author Jonas Keßler (jonas.kessler@acando.de)
 */
@RestController
public class ReactiveController {

	@Autowired
	private PersonRepository repository;

	@PostMapping("/persons")
	Completable createSomething(@RequestBody Single<Person> body) {
		Completable result = repository.savePerson(body);
		/*
		 * everything done here is executed before the save, because the server triggers the execution
		 */
		System.out.println("Moep");
		return result;
	}

	// @GetMapping(value = "/persons", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@GetMapping(value = "/persons")
	Observable<Person> allPersons() {
		return repository.allPeople();
	}

}
