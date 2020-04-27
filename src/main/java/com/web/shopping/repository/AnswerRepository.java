package com.web.shopping.repository;

import com.web.shopping.model.Answer;
import com.web.shopping.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,String> {


    List<Answer> findAllByQuestionId(String questionId);

    Page<Answer> findAll(Pageable pageable);
}
