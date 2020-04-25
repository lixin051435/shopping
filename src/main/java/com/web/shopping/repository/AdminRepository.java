package com.web.shopping.repository;

import com.web.shopping.model.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,String> {


    Page<Admin> findAllByNicknameLike(String nickname, Pageable pageable);

    Admin findByNicknameAndPassword(String nickname, String password);

}
