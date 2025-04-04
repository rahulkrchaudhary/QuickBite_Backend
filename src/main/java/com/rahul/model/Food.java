package com.rahul.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private Long price;

    @ManyToOne
    private Category foodCategory;

    @Column(length = 1000)
    @ElementCollection
    private List<String> images;

    private boolean available;

    @ManyToOne
    private Restaurant restaurant;

    private boolean isVegetarian;
    private boolean isSeasonal;

    @ManyToMany
    private List<IngredientsItem> ingredients= new ArrayList<>();

//    @Temporal(TemporalType.TIMESTAMP)
//    private Date creationDate = new Date();
//    private Date creationDate;

//    @CreationTimestamp
//    private LocalDateTime creationDate;

//    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//    private LocalDateTime creationDate;
//
//    @PrePersist
//    public void setCreationDate() {
//        this.creationDate = LocalDateTime.now();
//    }

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME")  // ✅ Force DATETIME without (6)
    private Date creationDate;



}
