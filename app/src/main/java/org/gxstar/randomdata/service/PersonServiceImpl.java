package org.gxstar.randomdata.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gxstar.randomdata.api.dto.DateOfBirthDto;
import org.gxstar.randomdata.api.dto.PersonDto;
import org.gxstar.randomdata.api.dto.ResultInfoDto;
import org.gxstar.randomdata.models.DateOfBirth;
import org.gxstar.randomdata.models.Person;
import org.gxstar.randomdata.models.PersonName;
import org.gxstar.randomdata.models.ResultInfo;
import org.gxstar.randomdata.repository.DateOfBirthRepository;
import org.gxstar.randomdata.repository.PersonNameRepository;
import org.gxstar.randomdata.repository.PersonRepository;
import org.gxstar.randomdata.repository.ResultInfoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {
    private final ResultInfoRepository resultInfoRepository;
    private final DateOfBirthRepository dateOfBirthRepository;
    private final PersonNameRepository personNameRepository;
    private final PersonRepository personRepository;


    @Override
    public void save(final PersonDto person, final ResultInfoDto info) {
        final DateOfBirthDto dobDto = person.dob();
        final DateOfBirth savedDOB = dateOfBirthRepository.saveDOB(dobDto);

        final ResultInfo resultInfo = resultInfoRepository.save(info);

        final PersonName savedName = personNameRepository.save(person.name());

        final Person entity = Person.ofEntity(savedName, savedDOB, resultInfo, person.gender(), person.phone(), person.email(), person.cell(), person.nat());
        personRepository.save(entity);
    }
}
