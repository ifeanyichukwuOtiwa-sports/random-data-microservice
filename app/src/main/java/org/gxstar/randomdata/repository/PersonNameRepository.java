package org.gxstar.randomdata.repository;

import org.gxstar.randomdata.api.dto.PersonNameDto;
import org.gxstar.randomdata.models.PersonName;

import java.util.List;

public interface PersonNameRepository {
    PersonName save(PersonNameDto personName);
    List<PersonName> findByTitle(String title);
    PersonName getById(Long id);
    List<PersonName> findByFirstName(String firstName);
    List<PersonName> findByLastName(String lastName);
    List<PersonName> findNameHas(String characters);
}
