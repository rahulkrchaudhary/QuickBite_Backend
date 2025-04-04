package com.rahul.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long orderId;
    private String paymentId;
    private Long amount;
    private String currency;
    private String paymentStatus;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME")  // ✅ Force DATETIME without (6)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "DATETIME")  // ✅ Ensure correct type
    private Date updatedAt;



    @ManyToOne
    private User user;

    // Getters and Setters
}

//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Payment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private Long orderId;
//    private String paymentMethod;
//    private String paymentStatus;
//    private double totalAmount;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdDate;
//}
