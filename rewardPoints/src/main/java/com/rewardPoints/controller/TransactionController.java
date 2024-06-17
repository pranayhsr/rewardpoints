package com.rewardPoints.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rewardPoints.dto.CustomerRewardPoints;
import com.rewardPoints.dto.TransactionRequestDTO;
import com.rewardPoints.dto.TransactionResponseDTO;
import com.rewardPoints.service.RewardPointsService;

@RestController
public class TransactionController {
		
	@Autowired
    private RewardPointsService  rewardPointsService;
	

	  @GetMapping("/reward-points")
	    public ResponseEntity<List<CustomerRewardPoints>> getCustomerRewardPoints() {
	        try {
	            List<CustomerRewardPoints> customerRewardPoints = rewardPointsService.getCustomerRewardPoints();
	            return new ResponseEntity<>(customerRewardPoints, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	  @GetMapping("/reward-points/{customerId}")
	    public ResponseEntity<List<CustomerRewardPoints>> getCustomerRewardPointsByCustomerId(@PathVariable Integer customerId) {
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

    
}
