package com.web.shopping.controller;

import com.web.shopping.model.Address;
import com.web.shopping.model.Customer;
import com.web.shopping.repository.AddressRepository;
import com.web.shopping.repository.CustomerRepository;
import com.web.shopping.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/list")
    public ResponseEntity list(@RequestParam(defaultValue="1",required = false) Integer page,
                               @RequestParam(defaultValue="10",required = false)Integer size,
                               @RequestParam(defaultValue = "",required = false) String name){
        Pageable pagebean =  PageRequest.of(page-1,size);
        name = "%" + name + "%";
        return ResponseEntity.ok(addressRepository.findAllByReceiverPhoneLike(name,pagebean));
    }

    @RequestMapping(path = "/save", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity save(@RequestBody Address form) {
        if(form.getAddressId() == null || form.getAddressId().trim().length() == 0){
            form.setAddressId(KeyUtil.genUniqueKey());
        }
        return ResponseEntity.ok(addressRepository.save(form));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity list(@PathVariable("id") String id ) {
        Address data = addressRepository.findById(id).get();
        addressRepository.deleteById(id);
        return ResponseEntity.ok(data);
    }

    @RequestMapping(path = "/weixin/save", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity save(String openid,String deliveryAddress,String receiverPhone,String reveiverName) {

        Address address = new Address();

        address.setAddressId(KeyUtil.genUniqueKey());
        address.setDeliveryAddress(deliveryAddress);
        address.setReceiverPhone(receiverPhone);
        address.setReveiverName(reveiverName);
        address.setCustomerId(customerRepository.findByOpenid(openid).getId());

        return ResponseEntity.ok(addressRepository.save(address));
    }

    @GetMapping("/weixin/getByOpenid")
    public ResponseEntity getByOpenid(String openid){
        Customer user = customerRepository.findByOpenid(openid);
        return ResponseEntity.ok(addressRepository.findAllByCustomerId(user.getId()));
    }

    @GetMapping("/findAllByCustomerId/{id}")
    public ResponseEntity findAllByCustomerId(@PathVariable("id") String id){
        return ResponseEntity.ok(addressRepository.findAllByCustomerId(id));
    }
}
