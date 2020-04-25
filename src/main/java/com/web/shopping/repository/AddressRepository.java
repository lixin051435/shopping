package com.web.shopping.repository;

import com.web.shopping.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,String> {

    Page<Address> findAllByReceiverPhoneLike(String phone,Pageable pageable);

    List<Address> findAllByCustomerId(String customerId);

}
