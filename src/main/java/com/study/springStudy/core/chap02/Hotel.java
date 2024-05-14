package com.study.springStudy.core.chap02;


/**
 * @solution
 * -먼저 dip를 해결하기 위해 구체적인 객체 대신
 * 추상적인 역할에 의존하게 코드를 개선.
 */
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
    private Restaurant restaurant = new AsianRestaurant();
    //헤드 쉐프
    private Chef headChef = new KimuraChef();

    // 호텔을 소개하는 기능
    public void inform() {
        System.out.printf("우리 호텔의 레스토랑은 %s 입니다. 그리고 헤드쉐프는 %s 입니다.\n"
        , restaurant.getClass().getSimpleName(),
                headChef.getClass().getSimpleName());

        restaurant.order();
    }
}
