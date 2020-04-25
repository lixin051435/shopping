package com.web.shopping.controller;

import com.web.shopping.model.Shipping;
import com.web.shopping.model.Customer;
import com.web.shopping.repository.ShippingRepository;
import com.web.shopping.repository.CustomerRepository;
import com.web.shopping.repository.ShippingRepository;
import com.web.shopping.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private ShippingRepository shippingRepository;


    @GetMapping("/list")
    public ResponseEntity list(@RequestParam(defaultValue="1",required = false) Integer page,
                               @RequestParam(defaultValue="10",required = false)Integer size,
                               @RequestParam(defaultValue = "",required = false) String name){
        Pageable pagebean =  PageRequest.of(page-1,size);
        name = "%" + name + "%";
        return ResponseEntity.ok(shippingRepository.findAllByShippingNameLike(name,pagebean));
    }

    @RequestMapping(path = "/save", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity save(@RequestBody Shipping form) {
        if(form.getShippingId() == null){
            form.setCreatetime(new Date());
        }
        return ResponseEntity.ok(shippingRepository.save(form));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity list(@PathVariable("id") Integer id ) {
        Shipping data = shippingRepository.findById(id).get();
        shippingRepository.deleteById(id);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/findAll")
    public ResponseEntity findAll(){
        return ResponseEntity.ok(shippingRepository.findAll());
    }
}
