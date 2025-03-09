package com.example.ecommerce.pms.controller;


import com.example.ecommerce.pms.entity.Orders;
import com.example.ecommerce.pms.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    //get the orders based on user login info so the params is empty
    @GetMapping("")
    public List<Orders> findAll() {
        //the username must passed from login credentials
        return ordersService.findAll();
    }

    //let them make a order and send them to order list where then can make individual payment of each order
    @PostMapping("/save/{productId}")
    public ResponseEntity<String> save(@RequestBody Orders orders, @PathVariable Long productId) {
        ordersService.save(orders, productId);
        return ResponseEntity.status(201).body("Order Saved");
    }


}
