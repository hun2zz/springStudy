package com.study.springStudy.chap01;

import com.study.springStudy.core.chap01.Hotel;
import org.junit.jupiter.api.Test;

class HotelTest {

    @Test
    void hotelTest() {
        Hotel hotel = new Hotel();
        hotel.inform();
    }

}