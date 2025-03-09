package com.example.ecommerce.pms.controller;

import com.example.ecommerce.pms.service.OrdersService;
import com.example.ecommerce.pms.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    OrdersService ordersService;

    //it provides the daily, weekly and monthly orders count
    @GetMapping("/orderReport")
    public Map<String, Long> getOrdersReport(){
        Map<String, Long> map = new HashMap<>();
        map.put("daily", ordersService.dailyReport());
        map.put("weekly", ordersService.weeklyReport());
        map.put("monthly", ordersService.monthlyReport());
        return map;
    }

    @GetMapping("/paymentReport")
    public Map<String, Double> getPaymentReport(){
        Map<String, Double> map = new HashMap<>();
        map.put("daily", paymentService.dailyReport());
        map.put("weekly", paymentService.weeklyReport());
        map.put("monthly", paymentService.monthlyReport());
        return map;
    }

    @GetMapping("/mostlyOrdered")
    public Map<String, String> getMostlyOrderedProduct(){
        Map<String, String> map = new HashMap<>();
        map.put("daily", ordersService.mostOrderedProductDaily().getName());
        map.put("weekly", ordersService.mostOrderedProductWeekly().getName());
        map.put("monthly", ordersService.mostOrderedProductMonthly().getName());
        return map;
    }
}
