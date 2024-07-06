package com.ebay.shipping.Service;

import com.ebay.shipping.Entity.Customer;
import com.ebay.shipping.Exceptions.customerIdNotFound;
import com.ebay.shipping.Exceptions.emailNotAvailable;
import com.ebay.shipping.Payloads.customerDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CustomerService {

    List<Customer> getCustomer();

    customerDto createCustomer(Customer customer) throws emailNotAvailable;

    customerDto updateCustomer(Long id, customerDto customerdto) throws customerIdNotFound, emailNotAvailable;

    void deleteCustomer(Long id) throws customerIdNotFound;
}
