package com.rahul.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User customer;

    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;

    private Long totalAmount;
    private String orderStatus;

//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdAt;

//    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//    private LocalDateTime createdAt;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME")  // ✅ Force DATETIME without (6)
    private Date createdAt;

//    @PrePersist
//    public void setCreatedAt() {
//        this.createdAt = LocalDateTime.now();
//    }

    @ManyToOne
    private Address deliveryAddress;

//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany
    private List<OrderItem> items;

    private int totalItem;
    private Long totalPrice;

    @OneToOne
    private Payment payment;


}
