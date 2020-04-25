package com.web.shopping.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * 商家订单表，商家和订单是多对多的关系
 * 商家是按照一个订单来发货的，而不是按照订单项发货的
 */
@Entity
@Table(name = "tb_business_order", schema = "db_shopping", catalog = "")
public class BusinessOrder {
    // 主键
    private String businessOrderId;
    // 订单编号
    private String orderNo;
    // 商家
    private String businessId;

    @Id
    @Column(name = "business_order_id")
    public String getBusinessOrderId() {
        return businessOrderId;
    }

    public void setBusinessOrderId(String businessOrderId) {
        this.businessOrderId = businessOrderId;
    }

    @Basic
    @Column(name = "order_no")
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Basic
    @Column(name = "business_no")
    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessOrder that = (BusinessOrder) o;
        return Objects.equals(businessOrderId, that.businessOrderId) &&
                Objects.equals(orderNo, that.orderNo) &&
                Objects.equals(businessId, that.businessId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(businessOrderId, orderNo, businessId);
    }
}
