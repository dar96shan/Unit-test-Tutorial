package com.ebay.shipping.Repository;

import com.ebay.shipping.Entity.Customer;
import com.ebay.shipping.Payloads.customerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.email = ?1")
    Optional<Customer> findByEmail(String email);
}
