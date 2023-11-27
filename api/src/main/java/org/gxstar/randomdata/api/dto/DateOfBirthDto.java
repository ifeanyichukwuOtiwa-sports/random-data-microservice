package org.gxstar.randomdata.api.dto;

import java.time.LocalDateTime;

public record DateOfBirthDto(
        LocalDateTime date,
        int age
) {
}
