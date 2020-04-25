package com.web.shopping.controller;

import com.web.shopping.model.Admin;
import com.web.shopping.repository.AdminRepository;
import com.web.shopping.repository.BusinessRepository;
import com.web.shopping.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @RequestMapping("/login")
    public ResponseEntity login(@RequestParam(defaultValue="") String nickname,
                                @RequestParam(defaultValue="") String password,
                                @RequestParam(defaultValue = "") Integer type){
        if(type == 1){
            Admin admin = adminRepository.findByNicknameAndPassword(nickname,password);
            return ResponseEntity.ok(admin);
        }else if(type == 0){
            return ResponseEntity.ok(customerRepository.findByNicknameAndPassword(nickname,password));
        }else{
            return ResponseEntity.ok(null);
        }

    }

}
