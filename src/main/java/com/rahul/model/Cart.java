package com.rahul.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name="customer_id")
    @JsonIgnore
    private User customer;

    private Long total;

    @OneToMany(mappedBy = "cart", cascade =CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items= new ArrayList<>();



}
