package com.web.shopping.controller;

import com.web.shopping.model.Customer;
import com.web.shopping.repository.CustomerRepository;
import com.web.shopping.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/list")
    public ResponseEntity list(@RequestParam(defaultValue="1",required = false) Integer page,
                               @RequestParam(defaultValue="10",required = false)Integer size,
                               @RequestParam(defaultValue = "",required = false) String name){
        Pageable pagebean =  PageRequest.of(page-1,size);
        name = "%" + name + "%";
        return ResponseEntity.ok(customerRepository.findAllByNicknameLike(name,pagebean));
    }

    @GetMapping("/findAll")
    public ResponseEntity findAll(){
        return ResponseEntity.ok(customerRepository.findAll());
    }

    @GetMapping("/getByOpenid")
    public ResponseEntity getByOpenid(String openid){
        Customer user = null;
        try{
            user = customerRepository.findByOpenid(openid);
        }catch (Exception e){
            System.out.println("根据openid没有查到用户");
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(path = "/save", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity save(@RequestBody Customer form) {
        if(form.getId() == null || form.getId().trim().length() == 0){
            form.setId(KeyUtil.genUniqueKey());
            form.setCreatetime(new Date());
        }
        return ResponseEntity.ok(customerRepository.save(form));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity list(@PathVariable("id") String id ) {
        Customer data = customerRepository.findById(id).get();
        customerRepository.deleteById(id);
        return ResponseEntity.ok(data);
    }
}
