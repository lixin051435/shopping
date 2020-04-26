package com.web.shopping.repository;

import com.web.shopping.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,String> {

    Page<Order> findAllByOrderNoLike(String orderNo,Pageable pageable);

    List<Order> findAllByCustomerId(String customerId);

    Page<Order> findAllByOrderNoIn(List<String> orderNos,Pageable pageable);

    Order findByOrderNo(String orderNo);
}
