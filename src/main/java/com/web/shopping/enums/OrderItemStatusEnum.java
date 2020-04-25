package com.web.shopping.enums;

public enum OrderItemStatusEnum {
    COMMITED(0,"已提交"),
    PAYED(1,"已付款"),
    SHIPPED(2,"已发货"),
    SUCCESS(3,"交易成功"),
    CLOSED(4,"交易关闭"),
    ;

    private int code;
    private String msg;

    OrderItemStatusEnum(int code, String msg) {
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
