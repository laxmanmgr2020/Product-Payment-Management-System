package com.example.ecommerce.pms.controller;

import com.example.ecommerce.pms.entity.ReportDTO;
import com.example.ecommerce.pms.service.OrdersService;
import com.example.ecommerce.pms.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public Map<String, List<ReportDTO>> getOrdersReport() {
        List<ReportDTO> reportList = new ArrayList<>();
        Map<String, List<ReportDTO>> map = new HashMap<>();
        reportList.add(new ReportDTO("daily", ordersService.dailyReport()));
        reportList.add(new ReportDTO("weekly", ordersService.weeklyReport()));
        reportList.add(new ReportDTO("monthly", ordersService.monthlyReport()));
        map.put("orderReport", reportList);
        return map;
    }

    @GetMapping("/paymentReport")
    public Map<String, List<ReportDTO>> getPaymentReport() {
        Map<String, List<ReportDTO>> map = new HashMap<>();
        List<ReportDTO> reportList = new ArrayList<>();
        reportList.add(new ReportDTO("daily", paymentService.dailyReport()));
        reportList.add(new ReportDTO("weekly", paymentService.weeklyReport()));
        reportList.add(new ReportDTO("monthly", paymentService.monthlyReport()));
        map.put("paymentReport", reportList);
        return map;
    }

    @GetMapping("/mostlyOrdered")
    public Map<String, String> getMostlyOrderedProduct() {
        Map<String, String> map = new HashMap<>();
        map.put("daily", ordersService.mostOrderedProductDaily());
        map.put("weekly", ordersService.mostOrderedProductWeekly());
        map.put("monthly", ordersService.mostOrderedProductMonthly());
        return map;
    }
}
