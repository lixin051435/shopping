package com.web.shopping.vo;

import java.util.List;

public class OrderVO {

    // 订单id
    private String id;

    // 买家id
    private String customerId;

    // 地址id
    private String addressId;

    // 购物车项目id列表
    private List<String> cartIds;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<String> getCartIds() {
        return cartIds;
    }

    public void setCartIds(List<String> cartIds) {
        this.cartIds = cartIds;
    }
}
