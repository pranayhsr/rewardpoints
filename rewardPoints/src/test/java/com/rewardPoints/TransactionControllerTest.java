package com.rewardPoints;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewardPoints.controller.TransactionController;
import com.rewardPoints.dto.CustomerRewardPoints;
import com.rewardPoints.dto.TransactionRequestDTO;
import com.rewardPoints.dto.TransactionResponseDTO;
import com.rewardPoints.service.RewardPointsService;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardPointsService rewardPointsService;

    private List<CustomerRewardPoints> customerRewardPointsList;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @InjectMocks
    private TransactionController transactionController;

    @Test
    public void testGetCustomerRewardPointsByInvalidCustomerId() throws Exception {
        when(rewardPointsService.getCustomerRewardPointsByCustomerId(100)).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get("/reward-points/{customerId}", 100))
                .andExpect(status().isNotFound());

        verify(rewardPointsService, times(1)).getCustomerRewardPointsByCustomerId(100);
        verifyNoMoreInteractions(rewardPointsService);
    }
    
    @BeforeEach
    public void setUp() {
        customerRewardPointsList = new ArrayList<>();
        CustomerRewardPoints customerRewardPoints = new CustomerRewardPoints() {
            private int customerId = 1;
            private String customerName = "John Doe";
            private String month = "2023-05";
            private int totalPoints = 150;

            @Override
            public Integer getCustomerId() {
                return customerId;
            }

            @Override
            public String getCustomerName() {
                return customerName;
            }

            @Override
            public String getMonth() {
                return month;
            }

            @Override
            public Integer getTotalPoints() {
                return totalPoints;
            }
        };
        customerRewardPointsList.add(customerRewardPoints);
    }
    
    @Test
    public void testGetCustomerRewardPoints_Success() throws Exception {
        when(rewardPointsService.getCustomerRewardPoints()).thenReturn(customerRewardPointsList);

        mockMvc.perform(get("/reward-points")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value(1))
                .andExpect(jsonPath("$[0].customerName").value("John Doe"))
                .andExpect(jsonPath("$[0].month").value("2023-05"))
                .andExpect(jsonPath("$[0].totalPoints").value(150));
    }
    
    @Test
    public void testGetCustomerRewardPoints_InternalServerError() throws Exception {
        when(rewardPointsService.getCustomerRewardPoints()).thenThrow(new RuntimeException("Internal Server Error"));

        mockMvc.perform(get("/reward-points")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
    
    @Test
    public void testGetCustomerRewardPointsByCustomerId_Success() throws Exception {
        int customerId = 1;
        when(rewardPointsService.getCustomerRewardPointsByCustomerId(customerId)).thenReturn(customerRewardPointsList);

        mockMvc.perform(get("/reward-points/{customerId}", customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value(1))
                .andExpect(jsonPath("$[0].customerName").value("John Doe"))
                .andExpect(jsonPath("$[0].month").value("2023-05"))
                .andExpect(jsonPath("$[0].totalPoints").value(150));
    }

    @Test
    public void testGetCustomerRewardPointsByCustomerId_NotFound() throws Exception {
        int customerId = 2; 
        when(rewardPointsService.getCustomerRewardPointsByCustomerId(customerId)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/reward-points/{customerId}", customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetCustomerRewardPointsByCustomerId_InternalServerError() throws Exception {
        int customerId = 1;
        when(rewardPointsService.getCustomerRewardPointsByCustomerId(customerId)).thenThrow(new RuntimeException("Internal Server Error"));

        mockMvc.perform(get("/reward-points/{customerId}", customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
    
    @Test
    public void testSaveTransactionAndRewardPoints_Success() throws Exception {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO();
        requestDTO.setCustomerId(1);
        requestDTO.setTransactionDate(new Date());
        requestDTO.setAmount(150.0);
        requestDTO.setDescription("Test transaction");

        TransactionResponseDTO responseDTO = new TransactionResponseDTO();
        responseDTO.setTransactionId(1);
        responseDTO.setCustomerId(requestDTO.getCustomerId());
        responseDTO.setAmount(requestDTO.getAmount());
        responseDTO.setPointsEarned(100); // Assuming points earned based on amount
        when(rewardPointsService.saveTransactionAndRewardPoints(any(TransactionRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionId").value(1))
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.amount").value(150.0))
                .andExpect(jsonPath("$.pointsEarned").value(100));
    }

    @Test
    public void testSaveTransactionAndRewardPoints_InternalServerError() throws Exception {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO();
        requestDTO.setCustomerId(1);
        requestDTO.setTransactionDate(new Date());
        requestDTO.setAmount(150.0);
        requestDTO.setDescription("Test transaction");

        when(rewardPointsService.saveTransactionAndRewardPoints(any(TransactionRequestDTO.class))).thenThrow(new RuntimeException("Internal Server Error"));

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isInternalServerError());
    }
//    @Test
//    public void testSaveTransactionAndRewardPoints_BadRequest() throws Exception {
//        TransactionRequestDTO requestDTO = new TransactionRequestDTO();
//        requestDTO.setTransactionDate(new Date());
//        requestDTO.setAmount(150.0);
//        requestDTO.setDescription("Test transaction");
//
//        mockMvc.perform(post("/transactions")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(requestDTO)))
//                .andExpect(status().isBadRequest());
//    }
}