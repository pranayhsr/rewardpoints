package com.rewardPoints.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
@Validated 
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
	    @PreAuthorize("hasRole('USER')")
	    public ResponseEntity<?> getCustomerRewardPointsByCustomerId(
	            @PathVariable
	            @Pattern(regexp = "\\d+", message = "Customer ID must be a number")
	            String customerId) {

	        try {
	            Integer customerIdInt = Integer.parseInt(customerId);

	            List<CustomerRewardPoints> customerRewardPoints = rewardPointsService.getCustomerRewardPointsByCustomerId(customerIdInt);
	            if (customerRewardPoints.isEmpty()) {
	                return ResponseEntity.notFound().build();
	            } else {
	                return ResponseEntity.ok(customerRewardPoints);
	            }
	        } catch (NumberFormatException e) {
	            return ResponseEntity.badRequest().body("Customer ID must be a valid number");
	        } catch (NoSuchElementException e) {
	            return ResponseEntity.notFound().build();
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
	    public ResponseEntity<Void> deleteTransactionById(@PathVariable @NotNull String id) {
	        try {
	            Integer transactionId = parseId(id);
	            boolean isDeleted = transactionService.deleteTransactionById(transactionId);
	            if (isDeleted) {
	                return ResponseEntity.noContent().build();
	            } else {
	                return ResponseEntity.notFound().build();
	            }
	        } catch (NumberFormatException e) {
	            return ResponseEntity.badRequest().build(); 
	        } catch (NoSuchElementException e) {
	            return ResponseEntity.notFound().build();
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }

	    private Integer parseId(String id) throws NumberFormatException {
	        return Integer.parseInt(id);
	    }
	    
	    @PutMapping("/updatetransactions/{transactionId}")
	    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @transactionSecurity.canUpdateTransaction(principal, #transactionId))")
	    public ResponseEntity<?> updateTransaction(
	            @PathVariable @Min(value = 1, message = "Transaction ID must be greater than or equal to 1") int transactionId,
	            @Valid @RequestBody Transaction updatedTransaction) {

	        try {
	            if (transactionId <= 0) {
	                return ResponseEntity.badRequest().body("Transaction ID must be greater than or equal to 1");
	            }

	            Transaction existingTransaction = transactionService.getTransactionById(transactionId);
	            if (existingTransaction == null) {
	                return ResponseEntity.notFound().build();
	            }

	            existingTransaction.setTransactionDate(updatedTransaction.getTransactionDate());
	            existingTransaction.setAmount(updatedTransaction.getAmount());
	            existingTransaction.setDescription(updatedTransaction.getDescription());

	            transactionService.saveTransaction(existingTransaction);
	            return ResponseEntity.ok("Transaction updated successfully");

	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update transaction: " + e.getMessage());
	        }
	    }
    
}
