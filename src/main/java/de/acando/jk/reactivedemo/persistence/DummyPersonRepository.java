/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * May be modified, original class by Arjen Poutsma : https://github.com/poutsma/web-function-sample
 */

package de.acando.jk.reactivedemo.persistence;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Arjen Poutsma
 */
@Service
public class DummyPersonRepository implements PersonRepository {

	private static final Log LOGGER = LogFactory.getLog(DummyPersonRepository.class);

	private final Map<Integer, Person> people = new HashMap<>();

	public DummyPersonRepository() {
		this.people.put(1, new Person("John Doe", 42));
		this.people.put(2, new Person("Jane Doe", 36));
	}

	@Override
	public Mono<Person> findById(Mono<Integer> id) {
		return id.map(i -> actualGet(i));
	}

	@Override
	public Flux<Person> findAll() {
		// TODO: call to map is executed directly because it is "the start of the chain"
		// is this because this repo is too dummy?
		// how does a "real" repo do this?

		return Flux.fromIterable(actualGetAll());
	}


	@Override
	public Mono<Void> save(Mono<Person> personMono) {
		return personMono.doOnNext(person -> {
			actualSave(person);
		}).thenEmpty(Mono.empty());
	}

	private Person actualGet(int id) {
		LOGGER.info("actualGet - NOW we access the data");
		return this.people.get(id);
	}

	private Collection<Person> actualGetAll() {
		LOGGER.info("actualGetAll - NOW we access the data");
		return this.people.values();
	}

	private void actualSave(Person person) {
		int id = people.size() + 1;
		this.people.put(id, person);
		LOGGER.info(String.format("Saved %s with id %d%n", person, id));
	}
}
