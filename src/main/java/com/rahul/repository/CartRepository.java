package com.rahul.repository;

import com.rahul.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    public Optional<Cart> findByCustomerId(Long userId);
//    Optional<Cart> findByCustomer_Id(Long UserId);


}
