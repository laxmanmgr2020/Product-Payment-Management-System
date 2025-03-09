package com.example.ecommerce.pms.repository;

import com.example.ecommerce.pms.entity.Orders;
import com.example.ecommerce.pms.entity.Product;
import com.example.ecommerce.pms.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    List<Orders> findAllByUserInfo(UserInfo userInfo);

    List<Orders> findAllByUserInfoOrderByOrderDateDesc(UserInfo userInfo);

    List<Orders> findAllByOrderByOrderDateDesc();

    Integer countAllByOrderDateBetween(LocalDate orderDateAfter, LocalDate orderDateBefore);

    Integer countAllByOrderDate(LocalDate orderDateAfter);

    @Query("SELECT o.product FROM Orders o " +
            "WHERE o.orderDate BETWEEN :startDate AND :endDate " +
            "GROUP BY o.product " +
            "order by count(*) DESC limit 1")
    Product findMostOrderedProduct(@Param("startDate") LocalDate startDate,
                                   @Param("endDate") LocalDate endDate);


}
