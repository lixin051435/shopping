package com.web.shopping.controller;

import com.web.shopping.model.Answer;
import com.web.shopping.repository.AnswerRepository;
import com.web.shopping.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;


    @GetMapping("/list")
    public ResponseEntity list(@RequestParam(defaultValue="1",required = false) Integer page,
                               @RequestParam(defaultValue="10",required = false)Integer size
                               ){
        Pageable pagebean =  PageRequest.of(page-1,size);
        return ResponseEntity.ok(answerRepository.findAll(pagebean));
    }

    @RequestMapping(path = "/save", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity save(@RequestBody Answer form) {
        if(form.getAnswerId() == null){
            form.setAnswerId(KeyUtil.genUniqueKey());
            form.setCreatetime(new Date());
        }
        return ResponseEntity.ok(answerRepository.save(form));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity list(@PathVariable("id") String id ) {
        Answer data = answerRepository.findById(id).get();
        answerRepository.deleteById(id);
        return ResponseEntity.ok(data);
    }

    }
