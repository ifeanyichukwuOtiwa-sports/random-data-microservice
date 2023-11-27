package org.gxstar.randomdata.repository;

import lombok.RequiredArgsConstructor;
import org.gxstar.randomdata.api.dto.PersonNameDto;
import org.gxstar.randomdata.models.PersonName;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class PersonNameRepositoryImpl implements PersonNameRepository {
    private final JdbcClient jdbcClient;
    @Override
    public PersonName save(final PersonNameDto personName) {
        final String sql = """
                INSERT INTO person_name(title, first, last)
                VALUES(:title, :first, :last)
                """;
        final SqlParameterSource params = new MapSqlParameterSource("title", personName.title())
                .addValue("first", personName.first())
                .addValue("last", personName.last());

        final KeyHolder key = new GeneratedKeyHolder();

        jdbcClient.sql(sql)
                .paramSource(params)
                .update(key);
        final long id = Objects.requireNonNull(key.getKey()).longValue();
        return getById(id);
    }

    @Override
    public List<PersonName> findByTitle(final String title) {
        final String sql = """
                SELECT id, title, first, last
                FROM person_name
                WHERE title = :title
                """;
        final SqlParameterSource params = new MapSqlParameterSource("title", title);
        return jdbcClient.sql(sql)
                .paramSource(params)
                .query(PersonName.class)
                .list();
    }

    @Override
    public PersonName getById(final Long id) {
        final String sql = """
                SELECT id, title, first, last
                FROM person_name
                WHERE id = :id
                """;
        final SqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbcClient.sql(sql)
                .paramSource(params)
                .query(PersonName.class)
                .single();
    }

    @Override
    public List<PersonName> findByFirstName(final String firstName) {
        final String sql = """
                SELECT id, title, first, last
                FROM person_name
                WHERE first = :first
                """;
        final SqlParameterSource params = new MapSqlParameterSource("first", firstName);
        return jdbcClient.sql(sql)
                .paramSource(params)
                .query(PersonName.class)
                .list();
    }

    @Override
    public List<PersonName> findByLastName(final String lastName) {
        final String sql = """
                SELECT id, title, first, last
                FROM person_name
                WHERE last = :last
                """;
        final SqlParameterSource params = new MapSqlParameterSource("last", lastName);
        return jdbcClient.sql(sql)
                .paramSource(params)
                .query(PersonName.class)
                .list();
    }

    @Override
    public List<PersonName> findNameHas(final String characters) {
        final String sql = """
                SELECT id, title, first, last
                FROM person_name
                WHERE CONCAT(first, ' ', last) LIKE CONCAT('%', :name, '%')
                    OR first LIKE CONCAT('%', :name, '%')
                    OR last LIKE CONCAT('%', :name, '%')
                """;
        final SqlParameterSource params = new MapSqlParameterSource("name", characters);
        return jdbcClient.sql(sql)
                .paramSource(params)
                .query(PersonName.class)
                .list();
    }
}
