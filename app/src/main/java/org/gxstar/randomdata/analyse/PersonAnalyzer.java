package org.gxstar.randomdata.analyse;

import org.gxstar.randomdata.models.Person;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface PersonAnalyzer {
    Map<LocalDateTime, List<Person>> getAllGroupingByMonth();
}
