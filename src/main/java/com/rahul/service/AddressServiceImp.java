package com.rahul.service;

import com.rahul.model.Address;
import com.rahul.model.User;
import com.rahul.repository.AddressRepository;
import com.rahul.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImp implements AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Address findShippingAddress(Address address, User user) {
//
        if(address.getId()==null){
            addressRepository.save(address);
        }
        Optional<Address> addressOptional = addressRepository.findById(address.getId());
//        Address address1 = null;
//        if(shippingAddress.isEmpty()){
//            address1 = addressRepository.save(address);
//        }else{
//            address1=shippingAddress.get();
//        }
        Address shippingAddress = addressOptional.get();

        if (!user.getAddresses().contains(shippingAddress)){
            user.getAddresses().add(shippingAddress);
            System.out.println("this is working");
            userRepository.save(user);
        }
        return shippingAddress;
    }
}
