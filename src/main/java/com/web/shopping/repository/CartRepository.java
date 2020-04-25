package com.web.shopping.repository;

import com.web.shopping.model.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,String> {

    Page<Cart> findAllByNickNameLike(String nickname, Pageable pageable);

    List<Cart> findAllByCustomerId(String customerId);

    List<Cart> findAllByCartIdIn(List<String> cartIds);

    @Transactional
    void deleteByCartIdIn(List<String> cartIds);
}
