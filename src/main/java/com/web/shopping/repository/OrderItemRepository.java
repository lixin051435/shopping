package com.web.shopping.repository;

import com.web.shopping.model.Orderitem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<Orderitem,String> {

    Page<Orderitem> findAllByOrderNo(String orderNo, Pageable pageable);

    List<Orderitem> findAllByOrderNo(String orderNo);

    @Transactional
    void deleteByOrderNo(String orderNo);


}
