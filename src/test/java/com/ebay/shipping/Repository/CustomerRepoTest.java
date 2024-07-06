package com.ebay.shipping.Repository;

import com.ebay.shipping.Entity.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepoTest {

    @Autowired
    private CustomerRepo customerRepo;

    @Test
    public void customerRepo_findbyemail(){

        //Arrange
        Customer customer = Customer.builder()
                .email("test@gmail.com")
                .name("test-name")
                .address("test")
                .build();

        //Customer save = customerRepo.save(customer);
        Customer save = customerRepo.save(customer);

        //Act
        Optional<Customer> byEmail = customerRepo.findByEmail(save.getEmail());

        //Assert
       assertThat(byEmail).isPresent();
       assertThat(byEmail.get().getEmail()).isEqualTo("test@gmail.com");
    }


}