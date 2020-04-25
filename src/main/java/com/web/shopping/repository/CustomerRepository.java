package com.web.shopping.repository;

import com.web.shopping.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,String> {

    Page<Customer> findAllByNicknameLike(String nickname, Pageable pageable);

    Customer findByOpenid(String openid);

    Customer findByNicknameAndPassword(String nickname,String password);
}
