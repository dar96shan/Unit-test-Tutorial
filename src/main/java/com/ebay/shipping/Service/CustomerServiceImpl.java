package com.ebay.shipping.Service;

import com.ebay.shipping.Entity.Customer;
import com.ebay.shipping.Exceptions.customerIdNotFound;
import com.ebay.shipping.Exceptions.emailNotAvailable;
import com.ebay.shipping.Payloads.customerDto;
import com.ebay.shipping.Repository.CustomerRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<Customer> getCustomer() {
        return customerRepo.findAll();
    }

    @Override
    public customerDto createCustomer(Customer customerReq) throws emailNotAvailable {

        Optional<Customer> customerEmail = customerRepo.findByEmail(customerReq.getEmail());

        if(customerEmail.isPresent()){
            throw new emailNotAvailable(customerReq.getEmail());
        }

        Customer customer = this.modelMapper.map(customerReq, Customer.class);
        Customer save = customerRepo.save(customer);
        return this.modelMapper.map(save, customerDto.class);
    }

    @Override
    public customerDto updateCustomer(Long id, customerDto customerdto) throws customerIdNotFound, emailNotAvailable {

        Customer customer = customerRepo.findById(id).orElseThrow(()-> new customerIdNotFound("customer",id));

        if(customerdto.getAddress() != null){
            customer.setAddress(customerdto.getAddress());
        }
        if(customerdto.getName() != null){
            customer.setName(customerdto.getName());
        }
        if(customerdto.getEmail() != null){
            Optional<Customer> byEmail = customerRepo.findByEmail(customerdto.getEmail());
            if(byEmail.isPresent()){
                throw new emailNotAvailable(customerdto.getEmail());
            }

            customer.setEmail(customerdto.getEmail());
        }

        Customer save = customerRepo.save(customer);

        return this.modelMapper.map(save, customerDto.class);
    }

    @Override
    public void deleteCustomer(Long id) throws customerIdNotFound {
        Customer customerId = customerRepo.findById(id).orElseThrow(() -> new customerIdNotFound("customer",id));
        customerRepo.delete(customerId);
    }
}
