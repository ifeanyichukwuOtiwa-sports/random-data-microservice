package org.gxstar.randomdata.repository;

import lombok.RequiredArgsConstructor;
import org.gxstar.randomdata.api.dto.DateOfBirthDto;
import org.gxstar.randomdata.models.DateOfBirth;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class DateOfBirthRepositoryImpl implements DateOfBirthRepository {
    private final JdbcClient jdbcClient;
    @Override
    public DateOfBirth saveDOB(final DateOfBirthDto dateOfBirth) {
        final String sql = """
                INSERT INTO date_of_birth(date, age)
                VALUES(:date, :age)
                """;
        final SqlParameterSource params = new MapSqlParameterSource("date", dateOfBirth.date())
                .addValue("age", dateOfBirth.age());
        final KeyHolder key = new GeneratedKeyHolder();
        jdbcClient.sql(sql)
                .paramSource(params)
                .update(key);
        final long id = Objects.requireNonNull(key.getKey()).longValue();
        return findDateOfBirtById(id);
    }

    @Override
    public DateOfBirth findDateOfBirtById(final Long id) {
        final String sql = """
                SELECT id, date, age
                FROM date_of_birth
                WHERE id = :id
                """;
        final SqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbcClient.sql(sql)
                .paramSource(params)
                .query(DateOfBirth.class)
                .single();
    }

    @Override
    public List<DateOfBirth> findDateOfBirthsBeforeDate(final LocalDateTime date) {
        final String sql = """
                SELECT id, date, age
                FROM date_of_birth
                WHERE date < :date
                """;
        final SqlParameterSource params = new MapSqlParameterSource("date", date);
        return jdbcClient.sql(sql)
                .paramSource(params)
                .query(DateOfBirth.class)
                .list();
    }

    @Override
    public List<DateOfBirth> findDateOfBirthsAfterDate(final LocalDateTime date) {
        final String sql = """
                SELECT id, date, age
                FROM date_of_birth
                WHERE date > :date
                """;
        final SqlParameterSource params = new MapSqlParameterSource("date", date);
        return jdbcClient.sql(sql)
                .paramSource(params)
                .query(DateOfBirth.class)
                .list();
    }
}
