package com.web.shopping.repository;

import com.web.shopping.model.Question;
import com.web.shopping.model.Shipping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,String> {

    List<Question> findByProductId(String productId);

    Page<Question> findAll(Pageable pageable);
}
