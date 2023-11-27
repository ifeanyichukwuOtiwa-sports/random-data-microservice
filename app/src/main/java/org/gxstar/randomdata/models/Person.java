package org.gxstar.randomdata.models;

public record Person(
        Long id,
        String gender,
        String phone,
        String email,
        String cell,
        String nat,
        PersonName name,
        DateOfBirth dateOfBirth,
        ResultInfo resultInfo
) {

    public static Person of(
            final Long id,
            final String gender,
            final String phone,
            final String email,
            final String cell,
            final String nat,
            final PersonName name,
            final DateOfBirth dateOfBirth,
            final ResultInfo resultInfo
    ) {
        return new Person(id, gender, phone, email, cell, nat, name, dateOfBirth, resultInfo);
    }

    public static Person ofEntity(
            final PersonName name,
            final DateOfBirth dateOfBirth,
            final ResultInfo resultInfo,
            final String gender,
            final String phone,
            final String email,
            final String cell,
            final String nat
    ) {
        return new Person(null, gender, phone, email, cell, nat, name, dateOfBirth, resultInfo);
    }
}
