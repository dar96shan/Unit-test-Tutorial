package com.ebay.shipping.Service;

import com.ebay.shipping.Entity.Customer;
import com.ebay.shipping.Exceptions.customerIdNotFound;
import com.ebay.shipping.Exceptions.emailNotAvailable;
import com.ebay.shipping.Payloads.customerDto;
import com.ebay.shipping.Repository.CustomerRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    public void CustomerService_createCustomer_returnCustomerDto() throws emailNotAvailable {

        //Arrange
        Customer customer = Customer.builder()
                .name("rahul")
                .email("rahul@email.com")
                .address("charlotte")
                .build();
        customerDto customerdto = customerDto.builder()
                .name("rahul")
                .email("rahul@email.com")
                .address("charlotte")
                .build();

        when(customerRepo.findByEmail(customer.getEmail())).thenReturn(Optional.empty());
        when(customerRepo.save(Mockito.any(Customer.class))).thenReturn(customer);
        when(modelMapper.map(Mockito.any(),Mockito.eq(Customer.class) )).thenReturn(customer);
        when(modelMapper.map(Mockito.any(), Mockito.eq(customerDto.class))).thenReturn(customerdto);

        //Act
        customerDto createCustomerdto = customerService.createCustomer(customer);

        //Assertion

        assertThat(createCustomerdto).isNotNull();
        assertThat(createCustomerdto.getEmail()).isEqualTo("rahul@email.com");

    }

    @Test
    public void CustomerService_getCustomer(){

        //Arrange
        Customer customer = Customer.builder()
                .name("rahul")
                .email("rahul@email.com")
                .address("charlotte")
                .build();
        Customer customer1 = Customer.builder()
                .name("krishna")
                .email("krishna@email.com")
                .address("India")
                .build();
        Customer customer3 = Customer.builder()
                .name("Nishi")
                .email("Nishi@email.com")
                .address("Banglore")
                .build();

        List<Customer> customerList = Arrays.asList(customer, customer1, customer3);

        when(customerRepo.findAll()).thenReturn(customerList);
        //Act
        List<Customer> getAllCustomer = customerService.getCustomer();

        //Assert

        assertThat(getAllCustomer).isNotNull();
        assertThat(getAllCustomer).hasSizeGreaterThan(0);
        assertThat(getAllCustomer).contains(customer,customer1,customer3);

    }

    @Test
    public void CustomerService_updateCustomer_whenFliedsareValid() throws emailNotAvailable, customerIdNotFound {

        //Arrange
        Customer customer = Customer.builder()
                .name("rahul")
                .email("rahul@email.com")
                .address("charlotte")
                .build();
        Customer updateCustomer = Customer.builder()
                .name("Ranga")
                .email("ranga@email.com")
                .address("Dubai")
                .build();
        customerDto updateCustomerdto = customerDto.builder()
                .name("Ranga")
                .email("ranga@email.com")
                .address("Dubai")
                .build();

        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepo.findByEmail(updateCustomerdto.getEmail())).thenReturn(Optional.empty());
        when(customerRepo.save(customer)).thenReturn(updateCustomer);
        when(modelMapper.map(updateCustomer, customerDto.class)).thenReturn(updateCustomerdto);

        //Act
        customerDto result = customerService.updateCustomer(1L, updateCustomerdto);

        //Assertion
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("ranga@email.com");
        assertThat(result.getAddress()).isEqualTo("Dubai");

        verify(customerRepo).findById(1L);
        verify(customerRepo).findByEmail(updateCustomerdto.getEmail());
        verify(customerRepo).save(customer);

    }

    @Test
    public void customerService_emailIsFound_throwsException() throws emailNotAvailable{
        Long customerId = 1L;
        //Arrange

        Customer existingCustomer = Customer.builder()
                .id(customerId)
                .name("kalki")
                .email("kalki@gmail.com")
                .address("india")
                .build();

        customerDto updatedCustomerDTO = customerDto.builder()
                .name("kalki-part1")
                .email("kalki@gmail.com")
                .address("india")
                .build();

        Customer alreadyDataCustomer = Customer.builder()
                .id(2L)
                .name("something")
                .email("kalki@gmail.com")
                .address("soomewhere")
                .build();

        when(customerRepo.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepo.findByEmail(existingCustomer.getEmail())).thenReturn(Optional.of(alreadyDataCustomer));
        //Act nd assert

        assertThrows(emailNotAvailable.class, ()->{customerService.updateCustomer(1L,updatedCustomerDTO);});

        //Assert

        verify(customerRepo).findById(customerId);
        verify(customerRepo).findByEmail(updatedCustomerDTO.getEmail());

    }

    @Test
    public void customerService_CustomerIdNotFound(){
        Long customerId = 1L;

        //Arrange

        customerDto updatedCustomerDTO = customerDto.builder()
                .name("kalki-part1")
                .email("kalki@gmail.com")
                .address("india")
                .build();


        when(customerRepo.findById(customerId)).thenReturn(Optional.empty());

        //Act and Assert
        assertThrows(customerIdNotFound.class, ()->customerService.updateCustomer(customerId,updatedCustomerDTO));

    }

    @Test
    public void customerService_DeleteCustomer(){
        Customer customer = Customer.builder()
                .name("sam")
                .address("cali")
                .email("sam@email.com")
                .build();

        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));

        assertAll(()-> {customerService.deleteCustomer(1L);});

    }







}