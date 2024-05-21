package com.study.springStudy.database.chap02;

import com.study.springStudy.database.chap01.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonMapperTest {

    @Autowired
    PersonMapper personMapper;
    @Test
    @DisplayName("마이바티스 매퍼로 사람 정보를 등록한다")
    void saveTest() {
        Person p = new Person(998, "진진자라", 30);
        boolean save = personMapper.save(p);

        assertTrue(save);

    }

    @Test
    @DisplayName("아이디로 사람 정보 삭제한다.")
    void delete() {
        //given
        long id = 9999;
        boolean flag = personMapper.delete(id);
        assertTrue(flag);
    }

    @Test
    @DisplayName("아이디가 998인 사람 정보를 수정한다.")
    void updateTest() {
        Person person = new Person(998, "도라에몽", 300);
        boolean flag = personMapper.update(person);
        assertTrue(flag);

    }

    
    @Test
    @DisplayName("전체 조회하면 결과 건수가 5건")
    void findAllTest() {
        //given
        //when
        List<Person> all = personMapper.findAll();

        //then
        all.forEach(System.out::println);
        assertEquals(5, all.size());
    }


    @Test
    @DisplayName("id가 일치하는 사람의 정보를 1명 조회한다.")
    void findOneTest() {
        //given
        long id = 300;
        //when

        //then
        Person person = personMapper.findOne(id);
        System.out.println("person = " + person);
        assertEquals("삼백이", person.getPersonName());
    }

    @Test
    @DisplayName("사람수와 이름리스트를 조회한다.")
    void findNamesTest() {
        //given

        //when
        List<String> names = personMapper.findNames();
        int count = personMapper.count();
        //then
        names.forEach(System.out::println);
        System.out.println();
        System.out.println("count = " + count);

    }




}