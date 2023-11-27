package org.gxstar.randomdata.models;

public record PersonName(
        Long id,
        String title,
        String first,
        String last
) {
    public static PersonName of(final Long id, final String title, final String first, final String last) {
        return new PersonName(id, title, first, last);
    }
}
