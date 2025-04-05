package com.rahul.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rahul.dto.RestaurantDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "app_user") // renamed here
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullname;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String Password;
    private USER_ROLE role=USER_ROLE.ROLE_CUSTOMER;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Order> orders= new ArrayList<>();

    @ElementCollection
    private List<RestaurantDto>favorites= new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses=new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE) // This removes cart items when a user is deleted.
    @JsonIgnore
    private List<Cart> cartItems = new ArrayList<>();

}
