package com.web.shopping.controller;

import com.web.shopping.model.Admin;
import com.web.shopping.repository.AdminRepository;
import com.web.shopping.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/list")
    public ResponseEntity list(@RequestParam(defaultValue="1",required = false) Integer page,
                               @RequestParam(defaultValue="10",required = false)Integer size,
                               @RequestParam(defaultValue = "",required = false) String name){
        Pageable pagebean =  PageRequest.of(page-1,size);
        name = "%" + name + "%";
        return ResponseEntity.ok(adminRepository.findAllByNicknameLike(name,pagebean));
    }

    @RequestMapping(path = "/save", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity save(@RequestBody Admin form) {
        if(form.getId() == null || form.getId().trim().length() == 0){
            form.setId(KeyUtil.genUniqueKey());
            form.setCreatetime(new Date());
        }
        return ResponseEntity.ok(adminRepository.save(form));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity list(@PathVariable("id") String id ) {
        Admin data = adminRepository.findById(id).get();
        adminRepository.deleteById(id);
        return ResponseEntity.ok(data);
    }
}
