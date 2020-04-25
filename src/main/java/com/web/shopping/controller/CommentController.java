package com.web.shopping.controller;

import com.web.shopping.model.Comment;
import com.web.shopping.model.Customer;
import com.web.shopping.model.Order;
import com.web.shopping.model.Orderitem;
import com.web.shopping.repository.CommentRepository;
import com.web.shopping.repository.CustomerRepository;
import com.web.shopping.repository.OrderItemRepository;
import com.web.shopping.repository.OrderRepository;
import com.web.shopping.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @GetMapping("/list")
    public ResponseEntity list(@RequestParam(defaultValue = "1", required = false) Integer page,
                               @RequestParam(defaultValue = "10", required = false) Integer size,
                               @RequestParam(defaultValue = "", required = false) String name,
                               @RequestParam(defaultValue = "", required = false) String no) {

        Pageable pagebean = PageRequest.of(page - 1, size);
        name = "%" + name + "%";
        if("".equals(no)){
            return ResponseEntity.ok(commentRepository.findAllByNicknameLike(name,pagebean));
        }else{
            return ResponseEntity.ok(commentRepository.findAllByUserIdAndNicknameLike(no,name,pagebean));
        }
    }

    @RequestMapping(path = "/save", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity save(@RequestBody Comment form) {
        if (form.getCommentId() == null || form.getCommentId().trim().length() == 0) {
            form.setCommentId(KeyUtil.genUniqueKey());
            form.setCreateTime(new Date());
        }
        Customer customer = customerRepository.findById(form.getUserId()).get();
        form.setNickname(customer.getNickname());
        return ResponseEntity.ok(commentRepository.save(form));
    }

    @RequestMapping(path = "/addBatch")
    public ResponseEntity addBatch(String orderNo,String customerId,String content) {
        Customer customer = customerRepository.findById(customerId).get();
        List<Orderitem> orderitems = orderItemRepository.findAllByOrderNo(orderNo);
        for (Orderitem o :
                orderitems) {
            Comment comment = new Comment();
            comment.setCommentId(KeyUtil.genUniqueKey());
            comment.setCreateTime(new Date());
            comment.setNickname(customer.getNickname());
            comment.setUserId(customerId);
            comment.setProductId(o.getProductId());
            comment.setContent(content);
            commentRepository.save(comment);
        }
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity list(@PathVariable("id") String id) {
        Comment data = commentRepository.findById(id).get();
        commentRepository.deleteById(id);
        return ResponseEntity.ok(data);
    }


    @GetMapping("/findAllByProductId/{id}")
    public ResponseEntity findAllByProductId(@PathVariable("id") String id) {
        return ResponseEntity.ok(commentRepository.findAllByProductId(id));
    }
}
