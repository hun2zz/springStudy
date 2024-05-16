package com.study.springStudy.springmvc.chap01;

public class OrderDto {
    private int orderNum;
    private String goods;
    private int amount;
    private int price;

    //Dto 방식에는 무조건 Setter, Getter를 만들어줘야 함. # 룰에 맞게


    public int getOrderNum() {
        return orderNum;
    }

    public String getGoods() {
        return goods;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "orderNum=" + orderNum +
                ", goods='" + goods + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
