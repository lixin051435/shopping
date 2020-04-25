package com.web.shopping.repository;

import com.web.shopping.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Page<Category> findAllByCategoryNameLike(String name, Pageable pageable);

}
