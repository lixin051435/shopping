package com.web.shopping.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.web.shopping.enums.OrderItemStatusEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tb_orderitem", schema = "db_shopping", catalog = "")
public class Orderitem {
    // 订单项主键
    private String id;
    // 下单客户id
    private String customerId;
    // 订单编号
    private String orderNo;
    // 商品所属商家id
    private String businessId;
    // 商品id
    private String productId;
    // 商品名称
    private String productName;
    // 商品图片
    private String productImage;
    // 下单时单价
    private BigDecimal currentUnitPrice;
    // 下单数量
    private Integer quantity;
    // 总价
    private BigDecimal totalPrice;
    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    // 订单项状态
    private Integer status = OrderItemStatusEnum.COMMITED.getCode();

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "business_id")
    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "customer_id")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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
    @Column(name = "product_id")
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "product_name")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Basic
    @Column(name = "product_image")
    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    @Basic
    @Column(name = "current_unit_price")
    public BigDecimal getCurrentUnitPrice() {
        return currentUnitPrice;
    }

    public void setCurrentUnitPrice(BigDecimal currentUnitPrice) {
        this.currentUnitPrice = currentUnitPrice;
    }

    @Basic
    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "total_price")
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orderitem orderitem = (Orderitem) o;
        return Objects.equals(id, orderitem.id) &&
                Objects.equals(customerId, orderitem.customerId) &&
                Objects.equals(orderNo, orderitem.orderNo) &&
                Objects.equals(productId, orderitem.productId) &&
                Objects.equals(productName, orderitem.productName) &&
                Objects.equals(productImage, orderitem.productImage) &&
                Objects.equals(currentUnitPrice, orderitem.currentUnitPrice) &&
                Objects.equals(quantity, orderitem.quantity) &&
                Objects.equals(totalPrice, orderitem.totalPrice) &&
                Objects.equals(createTime, orderitem.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, customerId, orderNo, productId, productName, productImage, currentUnitPrice, quantity, totalPrice, createTime);
    }
}
