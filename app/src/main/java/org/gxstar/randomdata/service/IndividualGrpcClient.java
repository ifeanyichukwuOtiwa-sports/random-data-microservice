package org.gxstar.randomdata.service;

import org.gxstar.randomdata.models.Person;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IndividualGrpcClient {
    Map<String, List<Person>> getPersons();
    Map.Entry<String, List<Person>> getPersonsCreatedBefore(final LocalDateTime time);
}
