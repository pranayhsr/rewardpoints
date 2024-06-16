
package com.rewardPoints.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewardPoints.dto.CustomerRewardPoints;
import com.rewardPoints.dto.TransactionRequestDTO;
import com.rewardPoints.dto.TransactionResponseDTO;
import com.rewardPoints.model.Customer;
import com.rewardPoints.model.RewardPoints;
import com.rewardPoints.model.Transaction;
import com.rewardPoints.repository.CustomerRepository;
import com.rewardPoints.repository.RewardPointsRepository;
import com.rewardPoints.repository.TransactionRepository;

@Service
public class RewardPointsService {

    @Autowired
    private RewardPointsRepository rewardPointsRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    CustomerRepository customerRepository;

    public List<CustomerRewardPoints> getCustomerRewardPoints() {
        return rewardPointsRepository.findCustomerRewardPoints();
    }
    
    public List<CustomerRewardPoints> getCustomerRewardPointsByCustomerId(Integer customerId) {
        return rewardPointsRepository.findCustomerRewardPointsByCustomerId(customerId);
    }
    
    public void saveRewardPoints(RewardPoints rewardPoints) {
        rewardPointsRepository.save(rewardPoints);
    }



    @Transactional
    public TransactionResponseDTO saveTransactionAndRewardPoints(TransactionRequestDTO requestDTO) {
        double amount = requestDTO.getAmount();
        int pointsEarned = calculateRewardPoints(amount);

        Customer customer = customerRepository.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setTransactionDate(requestDTO.getTransactionDate());
        transaction.setAmount(requestDTO.getAmount());
        transaction.setDescription(requestDTO.getDescription());

        transaction = transactionRepository.save(transaction);

        RewardPoints rewardPoints = new RewardPoints();
        rewardPoints.setCustomer(transaction.getCustomer());
        rewardPoints.setTransaction(transaction);
        rewardPoints.setPoints(pointsEarned);
        rewardPoints.setEarnedDate(new Date());

        rewardPointsRepository.save(rewardPoints);

        TransactionResponseDTO responseDTO = new TransactionResponseDTO();
        responseDTO.setTransactionId(transaction.getTransactionId());
        responseDTO.setCustomerId(transaction.getCustomer().getCustomerId());
        responseDTO.setAmount(transaction.getAmount());
        responseDTO.setPointsEarned(pointsEarned);

        return responseDTO;
    }

    private int calculateRewardPoints(double amount) {
        int points = 0;

        if (amount > 100) {
            points += (int) ((amount - 100) * 2); // 2 points for every dollar over $100
        }
        if (amount > 50) {
            points += (int) (Math.min(amount, 100) - 50); // 1 point for every dollar between $50 and $100
        }

        return points;
    }

}