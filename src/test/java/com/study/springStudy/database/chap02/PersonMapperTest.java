package com.study.springStudy.database.chap02;

import com.study.springStudy.database.chap01.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}