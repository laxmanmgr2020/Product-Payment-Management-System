package com.example.ecommerce.pms.service;

import com.example.ecommerce.pms.constants.OrderStatus;
import com.example.ecommerce.pms.entity.Orders;
import com.example.ecommerce.pms.entity.Product;
import com.example.ecommerce.pms.entity.UserInfo;
import com.example.ecommerce.pms.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    AuthService authService;

    public Orders findById(Long orderId) {
        Optional<Orders> orders = ordersRepository.findById(orderId);
        return orders.orElse(null);
    }

    public List<Orders> findAll() {
        if (Objects.equals(authService.getUserRole(), "ADMIN"))
            return ordersRepository.findAll();
        return ordersRepository.findAllByUserInfoOrderByOrderDateDesc((UserInfo) userService.loadUserByUsername(authService.getCurrentUsername()));
    }

    public void save(Orders orders, Long productId) {//tje email must be get from  the user claims
        Product product = productService.getProductById(productId);
        if (product.getQuantity() < orders.getQuantity()) {
            throw new IllegalArgumentException("Not enough quantity available for the product");
        }
        product.setQuantity(product.getQuantity() - orders.getQuantity());
        productService.save(product);

        orders.setOrderStatus(OrderStatus.IN_PROGRESS);

        orders.setProduct(productService.getProductById(productId));
        orders.setUserInfo((UserInfo) userService.loadUserByUsername(authService.getCurrentUsername()));
        ordersRepository.save(orders);
    }

    //most ordered items
    public Product mostOrderedProductDaily() {
        LocalDate today = LocalDate.now();
        return ordersRepository.findMostOrderedProduct(today, today);
    }

    public Product mostOrderedProductWeekly() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(WeekFields.of(Locale.getDefault()).getFirstDayOfWeek()).minusDays(7);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        return ordersRepository.findMostOrderedProduct(startOfWeek, endOfWeek);
    }

    public Product mostOrderedProductMonthly() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        return ordersRepository.findMostOrderedProduct(startOfMonth, endOfMonth);
    }

    //daily reports of order
    public long dailyReport() {
        LocalDate today = LocalDate.now();
        return ordersRepository.countAllByOrderDateBetween(today, today);
    }

    //weekly reports of order
    public long weeklyReport() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(WeekFields.of(Locale.getDefault()).getFirstDayOfWeek()).minusDays(7);
        System.out.println("startOfWeek = " + startOfWeek);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        System.out.println("endOfWeek = " + endOfWeek);
        return ordersRepository.countAllByOrderDateBetween(startOfWeek, endOfWeek);
    }

    //monthly reports of order
    public long monthlyReport() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        return ordersRepository.countAllByOrderDateBetween(startOfMonth, endOfMonth);
    }
}
