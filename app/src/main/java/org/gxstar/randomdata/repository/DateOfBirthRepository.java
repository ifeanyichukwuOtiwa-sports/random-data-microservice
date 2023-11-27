package org.gxstar.randomdata.repository;

import org.gxstar.randomdata.api.dto.DateOfBirthDto;
import org.gxstar.randomdata.models.DateOfBirth;

import java.time.LocalDateTime;
import java.util.List;

public interface DateOfBirthRepository {

    DateOfBirth saveDOB(DateOfBirthDto dateOfBirth);

    DateOfBirth findDateOfBirtById(Long id);

    List<DateOfBirth> findDateOfBirthsBeforeDate(final LocalDateTime date);
    List<DateOfBirth> findDateOfBirthsAfterDate(final LocalDateTime date);
}
