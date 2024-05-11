package com.dm4nk.unidbneo4j.bootstrap;

import com.dm4nk.unidbneo4j.model.Person;
import com.dm4nk.unidbneo4j.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@AllArgsConstructor
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final PersonRepository repository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        repository.deleteAll();

        Person person = Person.builder()
                .name("Dan")
                .build();

        Person teammate1 = Person.builder()
                .name("Jane")
                .build();

        Person teammate2 = Person.builder()
                .name("Dean")
                .build();

        person.setTeammates(Set.of(teammate1, teammate2));

        repository.save(person);

        person = repository.findByName("Dan");
        log.debug("Found person by Name: {}, teammates: {}", person, person.getTeammates());

        person = repository.findByTeammatesName("Jane").stream().findAny().get();
        log.debug("Found person by Teammate's Name: {}, teammates: {}", person, person.getTeammates());
    }
}
