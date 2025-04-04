package com.rahul.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
//    @OneToOne
    private User owner;

    private String name;
    private String description;
    private String cuisineType;

//    @OneToOne
    @ManyToOne
//    @JoinColumn(name = "address_id")
    private Address address;

    @Embedded
    private ContactInformation contactInformation;
    private String openingHours;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders= new ArrayList<>();

    @ElementCollection
    @Column(length = 1000)
    private List<String> images;
    private LocalDate registrationDate;
    private boolean open;
    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Food> foods= new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<IngredientsCategory> ingredientsCategories = new ArrayList<>();

}
