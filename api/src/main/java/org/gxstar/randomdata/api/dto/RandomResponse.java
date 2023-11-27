package org.gxstar.randomdata.api.dto;

import java.util.List;

public record RandomResponse(
        List<PersonDto> results,
        ResultInfoDto info
) {
}
