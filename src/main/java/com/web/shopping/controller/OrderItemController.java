package com.web.shopping.controller;

import com.web.shopping.model.Orderitem;
import com.web.shopping.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderitem")
public class OrderItemController {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @GetMapping("/list")
    public ResponseEntity list(@RequestParam(defaultValue="1",required = false) Integer page,
                               @RequestParam(defaultValue="10",required = false)Integer size,
                               @RequestParam(defaultValue = "",required = false) String orderno
                              ){
        Pageable pagebean =  PageRequest.of(page-1,size);
        return ResponseEntity.ok(orderItemRepository.findAllByOrderNo(orderno,pagebean));
    }

    @RequestMapping(path = "/save", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity save(@RequestBody Orderitem form) {
        return ResponseEntity.ok(orderItemRepository.save(form));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity list(@PathVariable("id") String id ) {
        Orderitem data = orderItemRepository.findById(id).get();
        orderItemRepository.deleteById(id);
        return ResponseEntity.ok(data);
    }
}
