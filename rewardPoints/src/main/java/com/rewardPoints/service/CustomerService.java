package com.rewardPoints.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewardPoints.model.Customer;
import com.rewardPoints.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
    private CustomerRepository customerRepository;
	
	 public Customer saveCustomer(Customer customer) {
	        return customerRepository.save(customer);
	    }

}
