package com.web.shopping.controller;

import com.web.shopping.model.Business;
import com.web.shopping.model.Category;
import com.web.shopping.model.Product;
import com.web.shopping.repository.BusinessRepository;
import com.web.shopping.repository.CategoryRepository;
import com.web.shopping.repository.ProductRepository;
import com.web.shopping.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @GetMapping("/list")
    public ResponseEntity list(@RequestParam(defaultValue="1",required = false) Integer page,
                               @RequestParam(defaultValue="10",required = false)Integer size,
                               @RequestParam(defaultValue = "",required = false) String name,
                               @RequestParam(defaultValue = "",required = false) String categoryName){
        Pageable pagebean =  PageRequest.of(page-1,size);
        name = "%" + name + "%";
        categoryName = "%" + categoryName + "%";
        return ResponseEntity.ok(productRepository.findAllByCategoryNameLikeAndNameLike(categoryName,name,pagebean));
    }

    @GetMapping("/findAll")
    public ResponseEntity findAll(){
        return ResponseEntity.ok(productRepository.findAll());
    }

    @RequestMapping(path = "/save", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity save(@RequestBody Product form) {
        if(form.getId() == null || form.getId().trim().length() == 0){
            form.setId(KeyUtil.genUniqueKey());
        }
        Optional<Category> categoryOptional = categoryRepository.findById(form.getCategoryId());
        if(categoryOptional.isPresent()){
            form.setCategoryName(categoryOptional.get().getCategoryName());
        }else{
            form.setCategoryName("");
        }

        Optional<Business> businessOptional = businessRepository.findById(form.getBusinessId());
        if(categoryOptional.isPresent()){
            form.setBusinessName(businessOptional.get().getRealname());
        }else{
            form.setBusinessName("");
        }
        return ResponseEntity.ok(productRepository.save(form));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity list(@PathVariable("id") String id ) {
        Product data = productRepository.findById(id).get();
        productRepository.deleteById(id);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/findByCategory/{id}")
    public ResponseEntity findByCategory(@PathVariable("id") Integer id ) {
        return ResponseEntity.ok(productRepository.findAllByCategoryId(id));
    }

    @GetMapping("/findPageByCategory")
    public ResponseEntity findPageByCategory(@RequestParam(defaultValue="1",required = false) Integer page,
                                             @RequestParam(defaultValue="10",required = false)Integer size,
                                             @RequestParam(defaultValue = "",required = false) String name,
                                             @RequestParam(defaultValue = "0",required = false) Integer categoryId) {

        Pageable pagebean =  PageRequest.of(page-1,size);
        name = "%" + name + "%";
        if(categoryId == 0){
            // 查询所有
            return ResponseEntity.ok(productRepository.findAllByNameLike(name,pagebean));
        }else{
            return ResponseEntity.ok(productRepository.findAllByCategoryIdAndNameLike(categoryId,name,pagebean));
        }

    }

    @GetMapping("/findById/{id}")
    public ResponseEntity findById(@PathVariable("id") String id ) {
        return ResponseEntity.ok(productRepository.findById(id).get());
    }
}
