package com.web.shopping.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tb_customer_vip", schema = "db_shopping", catalog = "")
public class CustomerVip {
    private int id;
    private String vipNo;
    private String vipName;
    private String vipDescription;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "vip_no")
    public String getVipNo() {
        return vipNo;
    }

    public void setVipNo(String vipNo) {
        this.vipNo = vipNo;
    }

    @Basic
    @Column(name = "vip_name")
    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    @Basic
    @Column(name = "vip_description")
    public String getVipDescription() {
        return vipDescription;
    }

    public void setVipDescription(String vipDescription) {
        this.vipDescription = vipDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerVip that = (CustomerVip) o;
        return id == that.id &&
                Objects.equals(vipNo, that.vipNo) &&
                Objects.equals(vipName, that.vipName) &&
                Objects.equals(vipDescription, that.vipDescription);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, vipNo, vipName, vipDescription);
    }
}
