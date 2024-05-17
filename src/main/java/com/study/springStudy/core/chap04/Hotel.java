package com.study.springStudy.core.chap04;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @solution
 * - 객체 생성의 제어권을 이 클래에서
 * 다른 클래스로 이전시킴.
 * ex ) new 생성자(); -> 이 문법을 담당클래스를 정해서 몰아서 수행시킴.
 * -호텔 객체 생성시 반드시 의존객체를 전달하도록 강요
 *
 *
 *
 * // 제어의 역전(IoC) : 객체 생성의 제어권을 외부로 넘긴다.
 *     // 의존성 주입(DI) : 외부에서 생성된 객체를 주입받는 개념
 *
 */
@Component // Hotel 의 객체 생성 제어권을 스프링에게 넘김.
public class Hotel {
    /*
     * @problem - 호텔 클래스에서 직접 객체를 생성하면
     *            나중에 의존객체를 변경해야 될 때
     *             직접 호텔 클래스를 수정해야 되므로
     *            OCP를 위반하게 됨.
     *            그리고 headChef가 변경되면 레스토랑 안에
     *              쉐프도 같이 바뀌어야 할 때 2군데를 수정해야 함.
     */
    //레스토랑

//    @Autowired //자동으로 객체를 주입해줘~
    // Autowired는 필드에도 가능하고, 세터에도 설정이 가능하다.
    private final Restaurant restaurant;
    //헤드 쉐프
//    @Autowired
    private final Chef headChef;


    //만약에 해당 클래스의 생성자가 단 한개뿐이라면 자동으로 @Autowired를 붙이는거기 때문에 생략이 가능한 것 이다.
    public Hotel(@Qualifier("western") Restaurant restaurant, Chef headChef) {
        this.restaurant = restaurant;
        this.headChef = headChef;
    }

    // 호텔을 소개하는 기능
    public void inform() {
        System.out.printf("우리 호텔의 레스토랑은 %s 입니다. 그리고 헤드쉐프는 %s 입니다.\n"
        , restaurant.getClass().getSimpleName(),
                headChef.getClass().getSimpleName());

        restaurant.order();
    }


//    @Autowired
//    public void setRestaurant(Restaurant restaurant) {
//        this.restaurant = restaurant;
//    }
//
////    @Autowired
//    public void setHeadChef(Chef headChef) {
//        this.headChef = headChef;
//    }
}
