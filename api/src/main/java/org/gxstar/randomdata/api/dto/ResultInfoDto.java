package org.gxstar.randomdata.api.dto;

public record ResultInfoDto(
        String seed,
        String results,
        String page,
        String version
) {
}
