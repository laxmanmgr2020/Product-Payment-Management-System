package com.example.ecommerce.pms.repository;

import com.example.ecommerce.pms.entity.Payment;
import com.example.ecommerce.pms.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByUserInfo(UserInfo userInfo);

    List<Payment> findAllByUserInfoOrderByPaymentDateDesc(UserInfo userInfo);

    List<Payment> findAllByOrderByPaymentDateDesc();

    Integer countAllByPaymentDateBetween(LocalDate orderDateAfter, LocalDate orderDateBefore);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    Double getTotalAmountBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
