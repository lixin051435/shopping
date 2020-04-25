package com.web.shopping.controller;

import com.web.shopping.model.Category;
import com.web.shopping.model.Product;
import com.web.shopping.repository.CategoryRepository;
import com.web.shopping.repository.ProductRepository;
import com.web.shopping.vo.CategoryVO;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping("/list")
    public ResponseEntity list(@RequestParam(defaultValue="1",required = false) Integer page,
                               @RequestParam(defaultValue="10",required = false)Integer size,
                               @RequestParam(defaultValue = "",required = false) String name){
        Pageable pagebean =  PageRequest.of(page-1,size);
        name = "%" + name + "%";
        return ResponseEntity.ok(categoryRepository.findAllByCategoryNameLike(name,pagebean));
    }

    @GetMapping("/findAll")
    public ResponseEntity findAll(){
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @RequestMapping(path = "/save", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity save(@RequestBody Category form) {
        return ResponseEntity.ok(categoryRepository.save(form));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity list(@PathVariable("id") Integer id ) {
        Category data = categoryRepository.findById(id).get();
        // 删除这个类别
        categoryRepository.deleteById(id);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/weixin/findAll")
    public ResponseEntity weixinFindAll(){

        List<CategoryVO> categoryVOS = new ArrayList<>();

        List<Category> categories = categoryRepository.findAll();
        for (Category category :
                categories) {
            CategoryVO categoryVO = new CategoryVO();
            BeanUtils.copyProperties(category,categoryVO);
            List<Product> products = productRepository.findAllByCategoryId(category.getCategoryId());
            categoryVO.setProducts(products);
            categoryVOS.add(categoryVO);
        }
        return ResponseEntity.ok(categoryVOS);
    }


}
