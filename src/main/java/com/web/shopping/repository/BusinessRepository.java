package com.web.shopping.repository;

import com.web.shopping.model.Business;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business,String> {
    Page<Business> findAllByNicknameLike(String name, Pageable pagebean);

    Business findByNicknameAndPassword(String nickname,String password);

}
