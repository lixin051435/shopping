package com.web.shopping.repository;

import com.web.shopping.model.BusinessOrder;
import com.web.shopping.model.Orderitem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BusinessOrderRepository extends JpaRepository<BusinessOrder,String> {

    Page<BusinessOrder> findAllByBusinessId(String businessId,Pageable pageable);

    List<BusinessOrder> findAllByBusinessId(String businessId);

    @Transactional
    void deleteByOrderNo(String orderNo);


}
