package com.web.shopping.controller;

import com.web.shopping.constant.VIPConstants;
import com.web.shopping.enums.OrderItemStatusEnum;
import com.web.shopping.enums.OrderStatusEnum;
import com.web.shopping.model.*;
import com.web.shopping.repository.*;
import com.web.shopping.util.KeyUtil;
import com.web.shopping.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private BusinessOrderRepository businessOrderRepository;

    @GetMapping("/shipOrder")
    public ResponseEntity list(@RequestParam(defaultValue="",required = false) Integer shippingId,
                               @RequestParam(defaultValue="",required = false)Integer postage,
                               @RequestParam(defaultValue = "",required = false) String orderId){
        Order order = orderRepository.findById(orderId).get();
        order.setSendTime(new Date());
        order.setStatus(OrderStatusEnum.SHIPPED.getCode());
        order.setShippingId(shippingId);
        order.setPostage(postage);
        return ResponseEntity.ok(orderRepository.save(order));
    }


    // 根据商家根据订单发货
    @GetMapping("/sendout")
    public ResponseEntity sendout(String orderNo,String busniessId){
        // 查询这个订单下 这个商家的订单项
        List<Orderitem> orderitems = orderItemRepository.findAllByOrderNoAndBusinessId(orderNo,busniessId);
        // 改变这些个订单项状态
        for (Orderitem temp :
                orderitems) {
            temp.setStatus(OrderItemStatusEnum.SHIPPED.getCode());
            orderItemRepository.save(temp);
        }

        // 如果这个订单的所有订单项都发货了，则改变订单的状态
        Order order = orderRepository.findByOrderNo(orderNo);
        List<Integer> statusList = orderItemRepository.findAllByOrderNo(orderNo).stream().map(e->e.getStatus()).collect(Collectors.toList());
        // 计算已经发货的订单项个数
        int sendout_total = 0;
        for (int i = 0; i < statusList.size(); i++) {
            if(statusList.get(i).equals(OrderItemStatusEnum.SHIPPED.getCode())){
                sendout_total++;
            }
        }
        // 订单项个数等于发货的订单项个数，修改订单状态为已发货
        if(sendout_total == statusList.size()){
            order.setStatus(OrderStatusEnum.SHIPPED.getCode());
            orderRepository.save(order);
        }
        return ResponseEntity.ok(true);
    }

    // 根据订单编号和商家名称 查询该订单下这个商家的订单项
    @GetMapping("/getOrderItemByBusiness")
    public ResponseEntity getOrderItemByBusiness(String orderNo,String busniessId){
        List<Orderitem> orderitems = orderItemRepository.findAllByOrderNo(orderNo);
        orderitems = orderitems.stream().filter(new Predicate<Orderitem>() {
            @Override
            public boolean test(Orderitem orderitem) {
                // 只保留这个商家的
                return busniessId.equals(orderitem.getBusinessId());
            }
        }).collect(Collectors.toList());
        return ResponseEntity.ok(orderitems);
    }

    @GetMapping("/getOrdersByBusinessId")
    public ResponseEntity getOrdersByBusinessId(@RequestParam(defaultValue="1",required = false) Integer page,
                                                @RequestParam(defaultValue="10",required = false)Integer size,
                                                @RequestParam(defaultValue = "",required = false) String busniessId){
        Pageable pagebean =  PageRequest.of(page-1,size);
        // 根据商家id 查询所有的订单编号
        List<BusinessOrder> businessOrders = businessOrderRepository.findAllByBusinessId(busniessId);
        List<String> orderNos = businessOrders.stream().map(e->e.getOrderNo()).collect(Collectors.toList());

        // 根据所有的订单编号 查询所有的订单
        Page<Order> orders = orderRepository.findAllByOrderNoIn(orderNos);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/getMyOrders")
    public ResponseEntity getMyOrders(String customerId){

        return ResponseEntity.ok(orderRepository.findAllByCustomerId(customerId));
    }

    @GetMapping("/list")
    public ResponseEntity shipOrder(@RequestParam(defaultValue="1",required = false) Integer page,
                               @RequestParam(defaultValue="10",required = false)Integer size,
                               @RequestParam(defaultValue = "",required = false) String name){
        Pageable pagebean =  PageRequest.of(page-1,size);
        name = "%" + name + "%";
        return ResponseEntity.ok(orderRepository.findAllByOrderNoLike(name,pagebean));
    }

    @GetMapping("/changeOrderStatus")
    public ResponseEntity changeOrderStatus( @RequestParam(defaultValue="",required = false)Integer status,
                               @RequestParam(defaultValue = "",required = false) String orderId){
        Order order = orderRepository.findById(orderId).get();
        if(status == OrderStatusEnum.SHIPPED.getCode()){
            order.setSendTime(new Date());
        }else if(status == OrderStatusEnum.CLOSED.getCode()){
            order.setCloseTime(new Date());
        }else if(status == OrderStatusEnum.PAYED.getCode()){
            order.setPaymentTime(new Date());
        }else if(status == OrderStatusEnum.SUCCESS.getCode()){
            order.setEndTime(new Date());
        }
        order.setStatus(status);
        return ResponseEntity.ok(orderRepository.save(order));
    }

    @RequestMapping(path = "/save", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity save(@RequestBody OrderVO form) {
        if(form.getCartIds().size() == 0){
            System.out.println("购物车无数据");
            return ResponseEntity.ok(null);
        }

        // 创建订单
        Order order = new Order();
        order.setCustomerId(form.getCustomerId());
        order.setId(form.getId());
        order.setAddressId(form.getAddressId());
        if(form.getId() == null || "".equals(form.getId())){
            // 如果没有订单主键 说明是新增订单
            order.setId(KeyUtil.genUniqueKey());
            order.setOrderNo(KeyUtil.genUniqueNO());
            order.setCreateTime(new Date());
            order.setStatus(OrderStatusEnum.PAYED.getCode());

            BigDecimal orderTotal = new BigDecimal(0);

            List<Cart> carts = cartRepository.findAllByCartIdIn(form.getCartIds());

            // 创建orderitem  插入购物项
            for (Cart cart :
                    carts) {
                Orderitem orderitem = new Orderitem();
                orderitem.setId(KeyUtil.genUniqueKey());
                orderitem.setOrderNo(order.getOrderNo());
                orderitem.setCustomerId(order.getCustomerId());
                Product product  = productRepository.findById(cart.getProductId()).get();
                orderitem.setBusinessId(product.getBusinessId());
                orderitem.setProductId(product.getId());
                orderitem.setProductImage(product.getImageurl());
                orderitem.setProductName(product.getName());
                orderitem.setCurrentUnitPrice(product.getPrice());
                orderitem.setQuantity(cart.getProductNumber());
                orderitem.setTotalPrice(product.getPrice().multiply(new BigDecimal(cart.getProductNumber())));
                orderitem.setCreateTime(new Date());
                orderItemRepository.save(orderitem);

                // 计算订单总额
                orderTotal = orderTotal.add(orderitem.getTotalPrice());
            }



            Customer customer = customerRepository.findById(form.getCustomerId()).get();
            if(customer.getVipLevel() == 0 || customer.getVipLevel() == null){
                // 设置订单总额
                order.setOrderTotal(orderTotal);
            }else{
                orderTotal = orderTotal.multiply(new BigDecimal(VIPConstants.CustomerVIP_DISCOUNT));
                order.setOrderTotal(orderTotal);
            }

            // 删除原来的购物车
            cartRepository.deleteByCartIdIn(form.getCartIds());

        }
        return ResponseEntity.ok(orderRepository.save(order));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity list(@PathVariable("id") String id ) {
        Order data = orderRepository.findById(id).get();

        // 删除orderitem表
        orderItemRepository.deleteByOrderNo(data.getOrderNo());
        // 删除Order表
        orderRepository.deleteById(id);
        return ResponseEntity.ok(data);
    }
}
