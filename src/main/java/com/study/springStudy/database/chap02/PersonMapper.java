package com.study.springStudy.database.chap02;

import com.study.springStudy.database.chap01.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface PersonMapper {

    //사람 전체 조회
    List<Person> findAll();
    //사람 개별조회
    Person findOne(long id);

    //사람 등록
    boolean save(Person p);

    //사람 수정
    boolean update(Person newPerson);

    //사람 삭제
    boolean delete (long id);
}
