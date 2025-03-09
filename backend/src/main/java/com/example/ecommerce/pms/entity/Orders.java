package com.example.ecommerce.pms.entity;

import com.example.ecommerce.pms.constants.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_info_id")
    private UserInfo userInfo;

    private LocalDate orderDate;

    @PrePersist
    protected void onCreate() {
        this.orderDate = LocalDate.now(); // Set the current date when inserting
    }

    private String address;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NonNull
    private Product product;

    @OneToOne(mappedBy = "orders")
    @JsonIgnore
    private Payment payment;
}
