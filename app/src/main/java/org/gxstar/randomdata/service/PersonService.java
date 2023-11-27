package org.gxstar.randomdata.service;

import org.gxstar.randomdata.api.dto.PersonDto;
import org.gxstar.randomdata.api.dto.ResultInfoDto;

public interface PersonService {
    void save(PersonDto persondto, final ResultInfoDto info);
}
