package com.ebay.shipping.Controller;

import com.ebay.shipping.Entity.Customer;
import com.ebay.shipping.Exceptions.emailNotAvailable;
import com.ebay.shipping.Payloads.customerDto;
import com.ebay.shipping.Service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer customer;
    private customerDto customerdto;
    private List<Customer> customerList;


    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .name("test")
                .email("test@gmail.com")
                .address("test place")
                .build();
        customerdto = customerDto.builder()
                .name("test")
                .email("test@gmail.com")
                .address("test place")
                .build();
        customerList = Arrays.asList(customer);
    }

    @Test
    public void customerController_create() throws Exception {

        when(customerService.createCustomer(customer)).thenReturn(customerdto);

        //Act
        mockMvc.perform(post("/api/v1/customer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer))) // Use customerhere
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.email").value("test@gmail.com"))
                .andExpect(jsonPath("$.address").value("test place"));
    }

    @Test
    public void customerController_getCustomer() throws Exception {

        when(customerService.getCustomer()).thenReturn(customerList);

        //act
        mockMvc.perform(get("/api/v1/customer/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("test"))
                .andExpect(jsonPath("$[0].email").value("test@gmail.com"))
                .andExpect(jsonPath("$[0].address").value("test place"));
    }

    @Test
    public void customerController_updateCustomer() throws Exception {
        Long customerId = 1L;

        when(customerService.updateCustomer(customerId, customerdto)).thenReturn(customerdto);

        // Act
        mockMvc.perform(put("/api/v1/customer/update/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerdto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.email").value("test@gmail.com"))
                .andExpect(jsonPath("$.address").value("test place"));
    }

    @Test
    public void customerController_deleteCustomer() throws Exception {
        Long customerId = 1L;

        doNothing().when(customerService).deleteCustomer(customerId);

        // Act
        mockMvc.perform(delete("/api/v1/customer/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Delete successful"))
                .andExpect(jsonPath("$.status").value(true));
    }


}