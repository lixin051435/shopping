package com.web.shopping.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tb_address", schema = "db_shopping", catalog = "")
public class Address {
    private String addressId;
    private String deliveryAddress;
    private String receiverPhone;
    private String reveiverName;
    private String customerId;

    @Id
    @Column(name = "address_id")
    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    @Basic
    @Column(name = "delivery_address")
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    @Basic
    @Column(name = "receiver_phone")
    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    @Basic
    @Column(name = "reveiver_name")
    public String getReveiverName() {
        return reveiverName;
    }

    public void setReveiverName(String reveiverName) {
        this.reveiverName = reveiverName;
    }

    @Basic
    @Column(name = "customer_id")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(addressId, address.addressId) &&
                Objects.equals(deliveryAddress, address.deliveryAddress) &&
                Objects.equals(receiverPhone, address.receiverPhone) &&
                Objects.equals(reveiverName, address.reveiverName) &&
                Objects.equals(customerId, address.customerId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(addressId, deliveryAddress, receiverPhone, reveiverName, customerId);
    }
}
