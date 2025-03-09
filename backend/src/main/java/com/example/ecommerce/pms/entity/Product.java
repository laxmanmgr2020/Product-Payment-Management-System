package com.example.ecommerce.pms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Long quantity;

    @Transient
    private String imagePath;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Orders> orders;
}
