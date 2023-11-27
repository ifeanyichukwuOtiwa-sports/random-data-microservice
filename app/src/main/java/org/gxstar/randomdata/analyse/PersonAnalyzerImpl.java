package org.gxstar.randomdata.analyse;

import lombok.RequiredArgsConstructor;
import org.gxstar.randomdata.models.Person;
import org.gxstar.randomdata.service.IndividualGrpcClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonAnalyzerImpl implements PersonAnalyzer {
    private final IndividualGrpcClient client;

    @Override
    public Map<LocalDateTime, List<Person>> getAllGroupingByMonth() {

        final Map<String, List<Person>> persons = client.getPersons();
        return persons.entrySet()
                .stream()
                .flatMap(k -> Map.of(LocalDateTime.parse(k.getKey()), k.getValue()).entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
