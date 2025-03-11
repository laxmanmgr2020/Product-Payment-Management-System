package com.example.ecommerce.pms.service;

import com.example.ecommerce.pms.constants.OrderStatus;
import com.example.ecommerce.pms.entity.Orders;
import com.example.ecommerce.pms.entity.Payment;
import com.example.ecommerce.pms.entity.UserInfo;
import com.example.ecommerce.pms.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    UserService userService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    AuthService authService;

    public List<Payment> findAll() {
        if (Objects.equals(authService.getUserRole(), "ADMIN"))
            return paymentRepository.findAll();
        return paymentRepository.findAllByUserInfoOrderByPaymentDateDesc((UserInfo) userService.loadUserByUsername(authService.getCurrentUsername()));
    }

    public void save(Payment payment, Long orderId) {
        Orders order = ordersService.findById(orderId);
        order.setOrderStatus(OrderStatus.DELIVERED);

        payment.setOrders(ordersService.findById(orderId));
        payment.setUserInfo((UserInfo) userService.loadUserByUsername(authService.getCurrentUsername()));
        paymentRepository.save(payment);
    }

    //daily reports of payment
    public double dailyReport() {
        LocalDate today = LocalDate.now();
        Optional<Double> dailyAmount = Optional.ofNullable(paymentRepository.getTotalAmountBetweenDates(today, today));
        return dailyAmount.orElse(0.0);
    }

    //weekly reports of payment
    public double weeklyReport() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(WeekFields.of(Locale.getDefault()).getFirstDayOfWeek()).minusDays(7);
        System.out.println("startOfWeek = " + startOfWeek);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        System.out.println("endOfWeek = " + endOfWeek);
        Optional<Double> weeklyAmount = Optional.ofNullable(paymentRepository.getTotalAmountBetweenDates(startOfWeek, endOfWeek));
        return weeklyAmount.orElse(0.0);
    }

    //monthly reports of payment
    public double monthlyReport() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        System.out.println("startOfMonth = " + startOfMonth);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        System.out.println("endOfMonth = " + endOfMonth);
        Optional<Double> monthlyAmount = Optional.ofNullable(paymentRepository.getTotalAmountBetweenDates(startOfMonth, endOfMonth));
        return monthlyAmount.orElse(0.0);
    }
}
