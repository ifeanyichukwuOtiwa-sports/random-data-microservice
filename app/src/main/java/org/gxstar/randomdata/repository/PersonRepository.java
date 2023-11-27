package org.gxstar.randomdata.repository;

import org.gxstar.randomdata.models.Person;

import java.util.List;

public interface PersonRepository {
    void save(Person person);
    List<Person> findByEmail(String email);
    List<Person> getAll();

}
