package com.rewardPoints.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rewardPoints.dto.CustomerRewardPoints;
import com.rewardPoints.dto.TransactionRequestDTO;
import com.rewardPoints.dto.TransactionResponseDTO;
import com.rewardPoints.model.Transaction;
import com.rewardPoints.service.RewardPointsService;
import com.rewardPoints.service.TransactionService;

@RestController
public class TransactionController {
		
	@Autowired
    private RewardPointsService  rewardPointsService;
	
	@Autowired
	TransactionService transactionService;
 

	  @GetMapping("/reward-points")
	  @PreAuthorize("(hasRole('USER') ")
	    public ResponseEntity<List<CustomerRewardPoints>> getCustomerRewardPoints() {
	        try {
	            List<CustomerRewardPoints> customerRewardPoints = rewardPointsService.getCustomerRewardPoints();
	            return new ResponseEntity<>(customerRewardPoints, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } 

	  @GetMapping("/reward-points/{customerId}")
	  @PreAuthorize("(hasRole('USER') ")
	    public ResponseEntity<List<CustomerRewardPoints>> getCustomerRewardPointsByCustomerId(@Min(1) @Max(7) @PathVariable Integer customerId) {
	        try {
	            List<CustomerRewardPoints> customerRewardPoints = rewardPointsService.getCustomerRewardPointsByCustomerId(customerId);
	            if (customerRewardPoints.isEmpty()) {
	                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	            } else {
	                return new ResponseEntity<>(customerRewardPoints, HttpStatus.OK);
	            }
	        } catch (NoSuchElementException e) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
 
	    @PostMapping("/transactions")
	    @PreAuthorize("(hasRole('USER') ")
	    public ResponseEntity<TransactionResponseDTO> saveTransactionAndRewardPoints(@RequestBody TransactionRequestDTO requestDTO) {
	        try {
	            TransactionResponseDTO responseDTO = rewardPointsService.saveTransactionAndRewardPoints(requestDTO);
	            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	        } catch (IllegalArgumentException e) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	    
	    @DeleteMapping("/transactions/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<Void> deleteTransactionById(@PathVariable Integer id) {
	        try {
	            boolean isDeleted = transactionService.deleteTransactionById(id);
	            if (isDeleted) {
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	            } else {
	                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	            }
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    
	    @PutMapping("/updatetransactions/{transactionId}")
	    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @transactionSecurity.canUpdateTransaction(principal, #transactionId))")
	    public ResponseEntity<String> updateTransaction(
	            @PathVariable int transactionId,
	            @RequestBody Transaction updatedTransaction) {

	        try {
	            Transaction existingTransaction = transactionService.getTransactionById(transactionId);
	            if (existingTransaction == null) {
	                return new ResponseEntity<>("Transaction not found with ID: " + transactionId, HttpStatus.NOT_FOUND);
	            }

	            existingTransaction.setTransactionDate(updatedTransaction.getTransactionDate());
	            existingTransaction.setAmount(updatedTransaction.getAmount());
	            existingTransaction.setDescription(updatedTransaction.getDescription());

	            transactionService.saveTransaction(existingTransaction);
	            return new ResponseEntity<>("Transaction updated successfully", HttpStatus.OK);

	        } catch (Exception e) {
	            return new ResponseEntity<>("Failed to update transaction: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
    
}
