package com.rewardPoints;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rewardPoints.dto.TransactionDTO;
import com.rewardPoints.model.Customer;
import com.rewardPoints.model.Transaction;
import com.rewardPoints.repository.CustomerRepository;
import com.rewardPoints.repository.TransactionRepository;
import com.rewardPoints.service.TransactionService;

public class TransactionServiceTest {

	 @Mock
	    private TransactionRepository transactionRepository;

	    @Mock
	    private CustomerRepository customerRepository;

	    @InjectMocks
	    private TransactionService transactionService;
	    
	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.initMocks(this);
	    }
	    
	    @Test
	    public void testConvertToEntity() {
	        TransactionDTO transactionDTO = new TransactionDTO();
	        transactionDTO.setAmount(100.0);
	        transactionDTO.setDescription("Test Transaction");

	        Transaction transaction = transactionService.convertToEntity(transactionDTO);

	        assertEquals(transactionDTO.getAmount(), transaction.getAmount());
	        assertEquals(transactionDTO.getDescription(), transaction.getDescription());
	    }

	    @Test
	    public void testGetCustomerById() {
	        Customer mockCustomer = new Customer();
	        mockCustomer.setCustomerId(1);
	        mockCustomer.setCustomerName("Test Customer");

	        when(customerRepository.findById(1)).thenReturn(Optional.of(mockCustomer));

	        Customer foundCustomer = transactionService.getCustomerById(1);

	        assertEquals(mockCustomer.getCustomerId(), foundCustomer.getCustomerId());
	        assertEquals(mockCustomer.getCustomerName(), foundCustomer.getCustomerName());
	    }

	    @Test
	    public void testGetCustomerById_CustomerNotFound() {
	        when(customerRepository.findById(1)).thenReturn(Optional.empty());

	        assertThrows(IllegalArgumentException.class, () -> {
	            transactionService.getCustomerById(1);
	        });
	    }

	    @Test
	    public void testSaveTransaction() {
	        Transaction transaction = new Transaction();
	        transaction.setAmount(100.0);
	        transaction.setDescription("Test Transaction");

	        transactionService.saveTransaction(transaction);

	        verify(transactionRepository).save(transaction);
	    }
}
