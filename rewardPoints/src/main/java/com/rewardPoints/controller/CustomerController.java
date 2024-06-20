package com.rewardPoints.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewardPoints.dto.CustomerDTO;
import com.rewardPoints.model.Customer;
import com.rewardPoints.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	   @Autowired
	    private CustomerService customerService;

	   
	   @PostMapping("/saveCustData")
	   @PreAuthorize("hasRole('ADMIN'), (hasRole('USER') ")
	   public ResponseEntity<Object> saveCustomers(@RequestBody List<CustomerDTO> customerDTOs) {
	       try {
	           List<Customer> savedCustomers = new ArrayList<>();
	           
	           for (CustomerDTO customerDTO : customerDTOs) {
	               Customer customer = new Customer();
	               customer.setCustomerName(customerDTO.getCustomerName());
	               customer.setEmail(customerDTO.getEmail());

	               Customer savedCustomer = customerService.saveCustomer(customer);
	               savedCustomers.add(savedCustomer);
	           }

	           return new ResponseEntity<>(savedCustomers, HttpStatus.CREATED);

	       } catch (IllegalArgumentException e) {
	           return new ResponseEntity<>("Invalid input: " + e.getMessage(), HttpStatus.BAD_REQUEST);

	       } catch (Exception e) {
	           return new ResponseEntity<>("An error occurred while saving the customer data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	       } 
	   }
}
