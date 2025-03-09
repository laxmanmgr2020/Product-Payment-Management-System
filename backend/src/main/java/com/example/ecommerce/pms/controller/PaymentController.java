package com.example.ecommerce.pms.controller;

import com.example.ecommerce.pms.entity.Payment;
import com.example.ecommerce.pms.service.OrdersService;
import com.example.ecommerce.pms.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private PaymentService paymentService;

    //get the orders based on user login info so the params is empty
    @GetMapping("")
    public List<Payment> findAll() {
        return paymentService.findAll();
    }

    @PostMapping("/save/{orderId}")
    public ResponseEntity<String> save(@RequestBody Payment payment, @PathVariable Long orderId) {
        paymentService.save(payment, orderId);
        return ResponseEntity.status(201).body("Payment saved");
    }
}
