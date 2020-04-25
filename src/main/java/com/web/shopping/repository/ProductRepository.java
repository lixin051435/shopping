package com.web.shopping.repository;

import com.web.shopping.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {

    Page<Product> findAllByCategoryNameLikeAndNameLike(String categoryName, String name, Pageable pageable);

    List<Product> findAllByCategoryId(Integer categoryId);

    Page<Product> findAllByCategoryIdAndNameLike(Integer categoryId,String name,Pageable pageable);
    Page<Product> findAllByNameLike(String name,Pageable pageable);
}
