package org.gxstar.randomdata.api.dto;

public record PersonDto(
        String gender,
        PersonNameDto name,
        DateOfBirthDto dob,
        String phone,
        String email,
        String cell,
        String nat
) {
}
