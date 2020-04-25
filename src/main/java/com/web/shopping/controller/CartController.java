package com.web.shopping.controller;

import com.web.shopping.model.Cart;
import com.web.shopping.model.Customer;
import com.web.shopping.model.Product;
import com.web.shopping.repository.CartRepository;
import com.web.shopping.repository.ProductRepository;
import com.web.shopping.repository.CustomerRepository;
import com.web.shopping.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/list")
    public ResponseEntity list(@RequestParam(defaultValue="1",required = false) Integer page,
                               @RequestParam(defaultValue="10",required = false)Integer size,
                               @RequestParam(defaultValue = "",required = false) String name){
        Pageable pagebean =  PageRequest.of(page-1,size);
        name = "%" + name + "%";
        return ResponseEntity.ok(cartRepository.findAllByNickNameLike(name,pagebean));
    }

    @RequestMapping(path = "/save", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity save(@RequestBody Cart form) {
        if (form.getCartId() == null || "".equals(form.getCartId())) {
            form.setCartId(KeyUtil.genUniqueKey());
            form.setCreateTime(new Date());
        }
        Customer user = customerRepository.findById(form.getCustomerId()).get();
        Product product = productRepository.findById(form.getProductId()).get();
        form.setNickName(user.getNickname());
        form.setProductImg(product.getImageurl());
        form.setProductName(product.getName());
        form.setProductPrice(product.getPrice());
        return ResponseEntity.ok(cartRepository.save(form));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity list(@PathVariable("id") String id ) {
        Cart data = cartRepository.findById(id).get();
        cartRepository.deleteById(id);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/getCartsByCustomerId/{id}")
    public ResponseEntity getCartsByCustomerId(@PathVariable("id") String id ) {
        return ResponseEntity.ok(cartRepository.findAllByCustomerId(id));
    }
}
