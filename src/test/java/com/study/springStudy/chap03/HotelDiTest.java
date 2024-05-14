package com.study.springStudy.chap03;

import com.study.springStudy.core.chap03.Hotel;
import com.study.springStudy.core.chap03.config.HotelManager;

class HotelDiTest {

    void diTest() {
        HotelManager hotelManager = new HotelManager();
        Hotel hotel = hotelManager.hotel();
        hotel.inform();
    }

}