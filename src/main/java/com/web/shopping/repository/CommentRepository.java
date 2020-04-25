package com.web.shopping.repository;

import com.web.shopping.model.Address;
import com.web.shopping.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,String> {

    Page<Comment> findAllByNicknameLike(String nickname,Pageable pageable);

    Page<Comment> findAllByUserIdAndNicknameLike(String customerId,String nickname,Pageable pageable);

    List<Comment> findAllByProductId(String productId);

    Page<Comment> findAllByProductId(String productId,Pageable pageable);

}
