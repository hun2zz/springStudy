package com.study.springStudy.database.chap01;

import com.study.springStudy.database.chap01.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SpringJdbc {

    private final JdbcTemplate template;

    //INSERT
    public int save(Person person) {
        String sql = "INSERT INTO tbl_person VALUES (?, ?, ?)";
        //성공하면 1 실패하면 0 이 나옴.
        return template.update(sql, person.getId(), person.getPersonName(), person.getPersonAge());
    }

    //DELETE
    public boolean delete(Long id) {
        String sql = "DELETE FROM tbl_person WHERE id = ?";
        int update = template.update(sql, id);
        return update == 1;
    }


    // UPDATE
    public boolean update(Person newPerson) {
        // 이름, 나이 수정
        String sql = "UPDATE tbl_person " +
                "SET person_name = ?, person_age = ? " +
                "WHERE id = ?";
        int flag = template.update(sql, newPerson.getPersonName()
                , newPerson.getPersonAge(), newPerson.getId());
        return flag == 1;
    }

    //SELECT : 다중행 조회
    public List<Person> findAll () {
        String sql = "SELECT * FROM tbl_person";
        List<Person> people = template.query(sql, new PersonMapper());
        return people;
    }

    //SELECT : 단일행 조회
    public Person findONe (long id) {
        String sql = "SELECT * FROM tbl_person WHERE id = ?";
        Person person = template.queryForObject(sql, (rs, n) -> new Person(rs),id);
        return person;

        // ResultSet 에다가 조회한 표를 가져왔는데 이걸 어디에 넣어줄까를 ? new Person 안에 넣어준것이다.
    }

    //내부 크랠스
    public static class PersonMapper implements RowMapper<Person>{

        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new Person(rs);
        }
    }
}