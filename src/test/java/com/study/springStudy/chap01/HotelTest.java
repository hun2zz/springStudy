package com.study.springStudy.hap01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HotelTest {

    @Test
    void hotelTest() {
        Hotel hotel = new Hotel();
        hotel.inform();
    }

}