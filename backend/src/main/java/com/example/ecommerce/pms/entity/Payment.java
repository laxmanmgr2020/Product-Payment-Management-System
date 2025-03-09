package com.example.ecommerce.pms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "user_info_id")
    private UserInfo userInfo;

    private Double amount;

    private LocalDate paymentDate;

    @PrePersist
    protected void onCreate() {
        this.paymentDate = LocalDate.now();
    }
}
