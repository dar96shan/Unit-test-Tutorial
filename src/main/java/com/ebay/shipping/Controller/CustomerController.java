package com.ebay.shipping.Controller;

import com.ebay.shipping.Entity.Customer;
import com.ebay.shipping.Exceptions.customerIdNotFound;
import com.ebay.shipping.Exceptions.emailNotAvailable;
import com.ebay.shipping.Payloads.ApiResponse;
import com.ebay.shipping.Payloads.customerDto;
import com.ebay.shipping.Repository.CustomerRepo;
import com.ebay.shipping.Service.CustomerService;
import com.ebay.shipping.Service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getCustomers(){
       return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomer());
    }

    @PostMapping("/")
    public ResponseEntity<customerDto> createCustomer(@RequestBody Customer customerReq) throws emailNotAvailable {

        customerDto customerCreated = customerService.createCustomer(customerReq);
        return new ResponseEntity<>(customerCreated,HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<customerDto> updateCustomer(@PathVariable Long id, @RequestBody customerDto customerReq) throws customerIdNotFound, emailNotAvailable {
        customerDto update = customerService.updateCustomer(id, customerReq);
        return new ResponseEntity<>(update,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable Long id) throws customerIdNotFound {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(new ApiResponse("Delete successful",true),HttpStatus.OK);
    }



}
