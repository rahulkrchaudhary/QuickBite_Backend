package com.rahul.service;

import com.rahul.model.Address;
import com.rahul.model.User;

public interface AddressService {
    public Address findShippingAddress(Address address, User user);
}
