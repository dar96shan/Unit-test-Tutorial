package com.ebay.shipping.Exceptions;

import com.ebay.shipping.Entity.Customer;
import lombok.Data;

import java.util.Optional;

@Data
public class emailNotAvailable extends Exception{

    String email;

    public emailNotAvailable(String email) {
        super(String.format("%s : email is not available to save",email));
        this.email = email;
    }
}
