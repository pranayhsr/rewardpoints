package com.rewardPoints.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewardPoints.dto.TransactionDTO;
import com.rewardPoints.model.Customer;
import com.rewardPoints.model.Transaction;
import com.rewardPoints.repository.CustomerRepository;
import com.rewardPoints.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Transaction convertToEntity(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDescription(transactionDTO.getDescription());
        return transaction;
    }

    public Customer getCustomerById(Integer customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

}
