package com.rewardPoints;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewardPoints.controller.CustomerController;
import com.rewardPoints.dto.CustomerDTO;
import com.rewardPoints.model.Customer;
import com.rewardPoints.service.CustomerService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

	 @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private CustomerService customerService;

	    @Autowired
	    private ObjectMapper objectMapper;

	    @Test
	    void testSaveCustomer_Success() throws Exception {
	        CustomerDTO customerDTO = new CustomerDTO();
	        customerDTO.setCustomerName("test");
	        customerDTO.setEmail("test@example.com");

	        Customer customer = new Customer();
	        customer.setCustomerId(1);
	        customer.setCustomerName("test");
	        customer.setEmail("test@example.com");

	        when(customerService.saveCustomer(any(Customer.class))).thenReturn(customer);

	        mockMvc.perform(post("/customers/saveCustData")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(customerDTO)))
	                .andExpect(status().isCreated())
	                .andExpect(content().json(objectMapper.writeValueAsString(customer)));
	    }

	    @Test
	    void testSaveCustomer_InvalidInput() throws Exception {
	        CustomerDTO customerDTO = new CustomerDTO();
	        customerDTO.setCustomerName("test");
	        customerDTO.setEmail("invalid-email");

	        doThrow(new IllegalArgumentException("Invalid email")).when(customerService).saveCustomer(any(Customer.class));

	        mockMvc.perform(post("/customers/saveCustData")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(customerDTO)))
	                .andExpect(status().isBadRequest())
	                .andExpect(content().string("Invalid input: Invalid email"));
	    }

	    @Test
	    void testSaveCustomer_GeneralException() throws Exception {
	        CustomerDTO customerDTO = new CustomerDTO();
	        customerDTO.setCustomerName("test");
	        customerDTO.setEmail("test@example.com");

	        doThrow(new RuntimeException("Database error")).when(customerService).saveCustomer(any(Customer.class));

	        mockMvc.perform(post("/customers/saveCustData")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(customerDTO)))
	                .andExpect(status().isInternalServerError())
	                .andExpect(content().string("An error occurred while saving the customer data: Database error"));
	    }
}
