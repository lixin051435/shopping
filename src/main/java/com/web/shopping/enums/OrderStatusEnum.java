package com.web.shopping.enums;

public enum OrderStatusEnum {
    CANCEL(0,"已取消"),
    UNPAY(10,"未付款"),
    PAYED(20,"已付款"),
    SHIPPED(40,"已发货"),
    SUCCESS(50,"交易成功"),
    CLOSED(60,"交易关闭"),
    ;

    private int code;
    private String msg;

    OrderStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {

        return code;
    }

    public String getMsg() {
        return msg;
    }
}
