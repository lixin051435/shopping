package com.web.shopping.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tb_category", schema = "db_shopping", catalog = "")
public class Category {
    private int categoryId;
    private String categoryNo;
    private String categoryName;

    @Id
    @Column(name = "category_id")
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "category_no")
    public String getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    @Basic
    @Column(name = "category_name")
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return categoryId == category.categoryId &&
                Objects.equals(categoryNo, category.categoryNo) &&
                Objects.equals(categoryName, category.categoryName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(categoryId, categoryNo, categoryName);
    }
}
