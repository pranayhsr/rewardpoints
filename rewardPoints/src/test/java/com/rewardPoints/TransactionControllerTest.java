package com.rewardPoints;


import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.rewardPoints.controller.TransactionController;
import com.rewardPoints.dto.CustomerRewardPoints;
import com.rewardPoints.model.Customer;
import com.rewardPoints.model.RewardPoints;
import com.rewardPoints.model.Transaction;
import com.rewardPoints.service.RewardPointsService;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RewardPointsService rewardPointsService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testGetCustomerRewardPoints() throws Exception {
//        // Mock data
//        Customer customer = new Customer(1, "John Doe", "john.doe@example.com", "1234567890", null, null);
//
//        Transaction transaction1 = new Transaction(1, customer, new Date(System.currentTimeMillis()), 100.0, "Transaction 1", null);
//        Transaction transaction2 = new Transaction(2, customer, new Date(System.currentTimeMillis()), 200.0, "Transaction 2", null);
//
//        RewardPoints rewardPoints1 = new RewardPoints(1, customer, transaction1, 10, new Date(System.currentTimeMillis()));
//        RewardPoints rewardPoints2 = new RewardPoints(2, customer, transaction2, 20, new Date(System.currentTimeMillis()));
//
//        List<CustomerRewardPoints> mockCustomerRewardPoints = Arrays.asList(
//                new CustomerRewardPoints(customer.getCustomerId(), customer.getCustomerName(), rewardPoints1.getPoints() + rewardPoints2.getPoints())
//        );
//
//        // Mock service method
//        when(rewardPointsService.getCustomerRewardPoints()).thenReturn(mockCustomerRewardPoints);
//
//        // Perform GET request and verify response
//        mockMvc.perform(MockMvcRequestBuilders.get("/reward-points"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(mockCustomerRewardPoints.size()));
//    }
//
//    @Test
//    public void testGetCustomerRewardPointsByCustomerId() throws Exception {
//        int customerId = 1;
//
//        // Mock data
//        Customer customer = new Customer(customerId, "John Doe", "john.doe@example.com", "1234567890", null, null);
//
//        Transaction transaction1 = new Transaction(1, customer, new Date(System.currentTimeMillis()), 100.0, "Transaction 1", null);
//        Transaction transaction2 = new Transaction(2, customer, new Date(System.currentTimeMillis()), 200.0, "Transaction 2", null);
//
//        RewardPoints rewardPoints1 = new RewardPoints(1, customer, transaction1, 10, new Date(System.currentTimeMillis()));
//        RewardPoints rewardPoints2 = new RewardPoints(2, customer, transaction2, 20, new Date(System.currentTimeMillis()));
//
//        List<CustomerRewardPoints> mockCustomerRewardPoints = Arrays.asList(
//                new CustomerRewardPoints(customer.getCustomerId(), customer.getCustomerName(), rewardPoints1.getPoints() + rewardPoints2.getPoints())
//        );
//
//        // Mock service method
//        when(rewardPointsService.getCustomerRewardPointsByCustomerId(customerId)).thenReturn(mockCustomerRewardPoints);
//
//        // Perform GET request and verify response
//        mockMvc.perform(MockMvcRequestBuilders.get("/reward-points/{customerId}", customerId))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(mockCustomerRewardPoints.size()));
//    }
}
