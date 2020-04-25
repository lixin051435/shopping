package com.web.shopping.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tb_admin", schema = "db_shopping", catalog = "")
public class Admin {
    // 管理员id
    private String id;
    // 管理员用户名（唯一）
    private String nickname;
    // 管理员真实姓名
    private String realname;
    // 管理员登录密码
    private String password;
    // 管理员微信openid
    private String openid;
    // 管理员账号状态
    private Integer status;
    // 管理员邮箱
    private String email;
    // 管理员联系方式
    private String phone;
    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createtime;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "realname")
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "openid")
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        Admin admin = (Admin) o;
        return Objects.equals(id, admin.id) &&
                Objects.equals(nickname, admin.nickname) &&
                Objects.equals(realname, admin.realname) &&
                Objects.equals(password, admin.password) &&
                Objects.equals(openid, admin.openid) &&
                Objects.equals(status, admin.status) &&
                Objects.equals(email, admin.email) &&
                Objects.equals(phone, admin.phone) &&
                Objects.equals(createtime, admin.createtime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, nickname, realname, password, openid, status, email, phone, createtime);
    }
}
