package com.web.shopping.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tb_shipping", schema = "db_shopping", catalog = "")
public class Shipping {
    private Integer shippingId;
    private String shippingNo;
    private String shippingName;
    private String shippingContact;
    private String shippingPhone;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createtime;

    @Id
    @Column(name = "shipping_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(int shippingId) {
        this.shippingId = shippingId;
    }

    @Basic
    @Column(name = "shipping_no")
    public String getShippingNo() {
        return shippingNo;
    }

    public void setShippingNo(String shippingNo) {
        this.shippingNo = shippingNo;
    }

    @Basic
    @Column(name = "shipping_name")
    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    @Basic
    @Column(name = "shipping_contact")
    public String getShippingContact() {
        return shippingContact;
    }

    public void setShippingContact(String shippingContact) {
        this.shippingContact = shippingContact;
    }

    @Basic
    @Column(name = "shipping_phone")
    public String getShippingPhone() {
        return shippingPhone;
    }

    public void setShippingPhone(String shippingPhone) {
        this.shippingPhone = shippingPhone;
    }

    @Basic
    @Column(name = "createtime")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipping shipping = (Shipping) o;
        return shippingId == shipping.shippingId &&
                Objects.equals(shippingNo, shipping.shippingNo) &&
                Objects.equals(shippingName, shipping.shippingName) &&
                Objects.equals(shippingContact, shipping.shippingContact) &&
                Objects.equals(shippingPhone, shipping.shippingPhone) &&
                Objects.equals(createtime, shipping.createtime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(shippingId, shippingNo, shippingName, shippingContact, shippingPhone, createtime);
    }
}
