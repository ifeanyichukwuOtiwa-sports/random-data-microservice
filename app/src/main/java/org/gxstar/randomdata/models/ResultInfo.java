package org.gxstar.randomdata.models;

public record ResultInfo(
        Long id,
        String seed,
        String results,
        String page,
        String version
) {

    public static ResultInfo of(final Long id, final String seed, final String results, final String page, final String version) {
        return new ResultInfo(id, seed, results, page, version);
    }
}
