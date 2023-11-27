package org.gxstar.randomdata.models;

import java.time.LocalDateTime;

public record DateOfBirth(
        Long id,
        LocalDateTime date,
        int age
) {
    public static DateOfBirth of(final Long id, final LocalDateTime date, final Integer age) {
        return new DateOfBirth(id, date, age);
    }
}
