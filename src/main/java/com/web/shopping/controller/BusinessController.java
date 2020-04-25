package com.web.shopping.controller;

import com.web.shopping.model.Business;
import com.web.shopping.repository.BusinessRepository;
import com.web.shopping.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private BusinessRepository businessRepository;

    @GetMapping("/list")
    public ResponseEntity list(@RequestParam(defaultValue="1",required = false) Integer page,
                               @RequestParam(defaultValue="10",required = false)Integer size,
                               @RequestParam(defaultValue = "",required = false) String name){
        Pageable pagebean =  PageRequest.of(page-1,size);
        name = "%" + name + "%";
        return ResponseEntity.ok(businessRepository.findAllByNicknameLike(name,pagebean));
    }

    @GetMapping("/findAll")
    public ResponseEntity findAll(){
        return ResponseEntity.ok(businessRepository.findAll());
    }

    @RequestMapping(path = "/save", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity save(@RequestBody Business form) {
        if(form.getId() == null || form.getId().trim().length() == 0){
            form.setId(KeyUtil.genUniqueKey());
            form.setCreatetime(new Date());
        }
        return ResponseEntity.ok(businessRepository.save(form));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity list(@PathVariable("id") String id ) {
        Business data = businessRepository.findById(id).get();
        businessRepository.deleteById(id);
        return ResponseEntity.ok(data);
    }
}
