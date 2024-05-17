package com.study.springStudy.core.chap04;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class HotelSpringDiTest {

    @Test
    void diTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HotelConfig.class);
        Hotel bean = context.getBean(Hotel.class);
        bean.inform();

    }

}