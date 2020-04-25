package com.web.shopping.repository;

import com.web.shopping.model.Address;
import com.web.shopping.model.Shipping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShippingRepository extends JpaRepository<Shipping,Integer> {

    Page<Shipping> findAllByShippingNameLike(String name,Pageable pageable);

}
