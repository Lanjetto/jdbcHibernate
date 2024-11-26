package com.nexign;

public class Order {
    private Long oderId;
    private String orderName;
    private Double cost;

    public Order(String orderName, Double cost) {
        this.orderName = orderName;
        this.cost = cost;
    }

    public Long getOderId() {
        return oderId;
    }

    public void setOderId(Long oderId) {
        this.oderId = oderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Order{" +
               "oderId=" + oderId +
               ", orderName='" + orderName + '\'' +
               ", cost=" + cost +
               '}';
    }
}
