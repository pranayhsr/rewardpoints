package com.rewardPoints.service;

import java.util.Optional;

import javax.transaction.Transactional;

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

    
    @Transactional
    public Transaction getTransactionById(int transactionId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        return optionalTransaction.orElse(null); // Return null if transaction not found
    }

    @Transactional
    public boolean deleteTransactionById(Integer id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction saveTransactions(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
