package com.study.springStudy.springmvc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void sTest() {
        Student student = new Student("진상훈",13,1);

        System.out.println("student = " + student);
    }

}