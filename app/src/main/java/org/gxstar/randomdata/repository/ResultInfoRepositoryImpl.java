package org.gxstar.randomdata.repository;

import lombok.RequiredArgsConstructor;
import org.gxstar.randomdata.api.dto.ResultInfoDto;
import org.gxstar.randomdata.models.ResultInfo;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ResultInfoRepositoryImpl implements ResultInfoRepository {
    private final JdbcClient jdbcClient;
    @Override
    public ResultInfo save(final ResultInfoDto infoDto) {
        final String sql = """
                INSERT INTO result_info(seed, results, page, version)
                VALUES(":seed", ":results", ":page", ":version")
                """;
        final SqlParameterSource params = new MapSqlParameterSource("seed", infoDto.seed())
                .addValue("results", infoDto.results())
                .addValue("page", infoDto.page())
                .addValue("version", infoDto.version());
        final KeyHolder key = new GeneratedKeyHolder();

        jdbcClient.sql(sql)
                .paramSource(params)
                .update(key);
        final long id = Objects.requireNonNull(key.getKey()).longValue();
        return getById(id);
    }

    @Override
    public ResultInfo getById(final Long id) {
        final String sql = """
                SELECT id, seed, results, page, version
                FROM result_info
                WHERE id = :id
                """;
        final SqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbcClient.sql(sql)
                .paramSource(params)
                .query(ResultInfo.class)
                .single();
    }

    @Override
    public List<ResultInfo> getForSeeds(final String seed) {
        final String sql = """
                SELECT id, seed, results, page, version
                FROM result_info
                WHERE seed = :seed
                """;
        final SqlParameterSource params = new MapSqlParameterSource("seed", seed);
        return jdbcClient.sql(sql)
                .paramSource(params)
                .query(ResultInfo.class)
                .list();
    }
}
