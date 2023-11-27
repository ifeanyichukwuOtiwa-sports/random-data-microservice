package org.gxstar.randomdata.repository;

import lombok.RequiredArgsConstructor;
import org.gxstar.randomdata.models.DateOfBirth;
import org.gxstar.randomdata.models.Person;
import org.gxstar.randomdata.models.PersonName;
import org.gxstar.randomdata.models.ResultInfo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PersonRepositoryImpl implements PersonRepository {
    private final JdbcClient jdbcClient;
    @Override
    public void save(final Person person) {
        final String sql = """
                INSERT INTO person(gender, phone, email, cell, nat, fk_person_name, fk_date_of_birth, fk_result_info)
                VALUES(:gender, :phone, :email, :cell, :nat, :fk_person_name, :fk_dob_id, :fk_result_info)
                """;
        final SqlParameterSource params = new MapSqlParameterSource("gender", person.gender())
                .addValue("phone", person.phone())
                .addValue("email", person.email())
                .addValue("cell", person.cell())
                .addValue("nat", person.nat())
                .addValue("fk_person_name", person.name().id())
                .addValue("fk_dob_id", person.dateOfBirth().id())
                .addValue("fk_result_info", person.resultInfo().id());
        jdbcClient.sql(sql)
                .paramSource(params)
                .update();
    }

    @Override
    public List<Person> findByEmail(final String email) {
        final String sql = """
                SELECT p.id, gender, phone, email, cell, nat, n.id, title, first, last, d.id, date, age, r.id, seed, results, page, version
                FROM person p
                INNER JOIN person_name n ON p.fk_person_name = n.id
                INNER JOIN date_of_birth d ON p.fk_date_of_birth = d.id
                INNER JOIN result_info r ON p.fk_result_info = r.id
                WHERE email = :email
                """;
        final SqlParameterSource params = new MapSqlParameterSource("email", email);
        return jdbcClient.sql(sql)
                .paramSource(params)
                .query(this.rowMapper())
                .list();
    }


    @Override
    public List<Person> getAll() {
        final String sql = """
                SELECT p.id, gender, phone, email, cell, nat, n.id, title, first, last, d.id, date, age, r.id, seed, results, page, version
                FROM person p
                INNER JOIN person_name n ON p.fk_person_name = n.id
                INNER JOIN date_of_birth d ON p.fk_date_of_birth = d.id
                INNER JOIN result_info r ON p.fk_result_info = r.id
                """;
        final SqlParameterSource params = new MapSqlParameterSource();
        return jdbcClient.sql(sql)
                .paramSource(params)
                .query(this.rowMapper())
                .list();
    }



    private RowMapper<Person> rowMapper() {
        return (rs, rowNum) ->
            Person.of(
                    rs.getLong("p.id"),
                    rs.getString("gender"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("cell"),
                    rs.getString("nat"),
                    PersonName.of(
                            rs.getLong("n.id"),
                            rs.getString("title"),
                            rs.getString("first"),
                            rs.getString("last")
                    ),
                    DateOfBirth.of(
                            rs.getLong("d.id"),
                            rs.getTimestamp("date").toLocalDateTime(),
                            rs.getInt("age")
                    ),
                    ResultInfo.of(
                           rs.getLong("r.id"),
                            rs.getString("seed"),
                            rs.getString("results"),
                            rs.getString("page"),
                            rs.getString("version")
                    )

            );
    }
}
