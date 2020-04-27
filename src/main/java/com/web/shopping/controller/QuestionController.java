package com.web.shopping.controller;

import com.web.shopping.model.Answer;
import com.web.shopping.model.Question;
import com.web.shopping.repository.AnswerRepository;
import com.web.shopping.repository.QuestionRepository;
import com.web.shopping.util.KeyUtil;
import com.web.shopping.vo.QuestionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.security.krb5.internal.crypto.KeyUsage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;


    @GetMapping("/list")
    public ResponseEntity list(@RequestParam(defaultValue="1",required = false) Integer page,
                               @RequestParam(defaultValue="10",required = false)Integer size){
        Pageable pagebean =  PageRequest.of(page-1,size);
        return ResponseEntity.ok(questionRepository.findAll(pagebean));
    }

    @RequestMapping(path = "/save", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity save(@RequestBody Question form) {
        if(form.getQuestionId() == null){
            form.setQuestionId(KeyUtil.genUniqueKey());
            form.setCreatetime(new Date());
        }
        return ResponseEntity.ok(questionRepository.save(form));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity list(@PathVariable("id") String id ) {
        Question data = questionRepository.findById(id).get();
        questionRepository.deleteById(id);
        return ResponseEntity.ok(data);
    }

    /**
     * 获取所有问题 含答案
     * @param productId
     * @return
     */
    @GetMapping("/findAll")
    public ResponseEntity findAll(String productId){
        List<QuestionVO> questionVOS = new ArrayList<>();

        List<Question> questions = questionRepository.findByProductId(productId);
        for (Question q :
                questions) {
            QuestionVO questionVO = new QuestionVO();
            BeanUtils.copyProperties(q,questionVO);

            List<Answer> answers = answerRepository.findAllByQuestionId(q.getQuestionId());
            questionVO.setAnswerList(answers);

            questionVOS.add(questionVO);
        }
        return ResponseEntity.ok(questionVOS);
    }

    /**
     * 获取所有问题
     * @return
     */
    @GetMapping("/findRealAll")
    public ResponseEntity findRealAll(){
        return ResponseEntity.ok(questionRepository.findAll());
    }
}
