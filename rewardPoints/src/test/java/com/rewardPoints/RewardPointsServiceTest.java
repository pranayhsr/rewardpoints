package com.rewardPoints;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rewardPoints.dto.TransactionRequestDTO;
import com.rewardPoints.dto.TransactionResponseDTO;
import com.rewardPoints.model.Customer;
import com.rewardPoints.model.Transaction;
import com.rewardPoints.repository.CustomerRepository;
import com.rewardPoints.repository.RewardPointsRepository;
import com.rewardPoints.repository.TransactionRepository;
import com.rewardPoints.service.RewardPointsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebMvcTest(RewardPointsService.class)
public class RewardPointsServiceTest {
    @Mock
    private RewardPointsRepository rewardPointsRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private RewardPointsService rewardPointsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveTransactionAndRewardPoints() {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO();
        requestDTO.setCustomerId(1);
        requestDTO.setTransactionDate(new Date());
        requestDTO.setAmount(120.0);
        requestDTO.setDescription("Test Transaction");

        Customer customer = new Customer();
        customer.setCustomerId(1);

        Transaction transaction = new Transaction();
        transaction.setTransactionId(1);
        transaction.setCustomer(customer);
        transaction.setTransactionDate(requestDTO.getTransactionDate());
        transaction.setAmount(requestDTO.getAmount());
        transaction.setDescription(requestDTO.getDescription());

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionResponseDTO responseDTO = rewardPointsService.saveTransactionAndRewardPoints(requestDTO);

        verify(transactionRepository).save(any(Transaction.class));

        assertEquals(transaction.getTransactionId(), responseDTO.getTransactionId());
        assertEquals(transaction.getCustomer().getCustomerId(), responseDTO.getCustomerId());
        assertEquals(transaction.getAmount(), responseDTO.getAmount());
        assertEquals(44, responseDTO.getPointsEarned()); 
    }

}
