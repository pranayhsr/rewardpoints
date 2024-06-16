package com.rewardPoints.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import com.rewardPoints.dto.CustomerRewardPoints;
import com.rewardPoints.model.RewardPoints;

@Repository
public interface RewardPointsRepository extends JpaRepository<RewardPoints, Integer> {
	

	   @Query("SELECT c.customerId AS customerId, c.customerName AS customerName, " +
	           "FUNCTION('DATE_FORMAT', rp.earnedDate, '%Y-%m') AS month, SUM(rp.points) AS totalPoints " +
	           "FROM Customer c " +
	           "JOIN RewardPoints rp ON c.customerId = rp.customer.customerId " +
	           "GROUP BY c.customerId, c.customerName, FUNCTION('DATE_FORMAT', rp.earnedDate, '%Y-%m') " +
	           "ORDER BY c.customerId, month")
	    List<CustomerRewardPoints> findCustomerRewardPoints();
	   
	   @Query("SELECT c.customerId AS customerId, c.customerName AS customerName, " +
	           "FUNCTION('DATE_FORMAT', rp.earnedDate, '%Y-%m') AS month, SUM(rp.points) AS totalPoints " +
	           "FROM Customer c " +
	           "JOIN RewardPoints rp ON c.customerId = rp.customer.customerId " +
	           "WHERE c.customerId = :customerId " +
	           "GROUP BY c.customerId, c.customerName, FUNCTION('DATE_FORMAT', rp.earnedDate, '%Y-%m') " +
	           "ORDER BY month")
	    List<CustomerRewardPoints> findCustomerRewardPointsByCustomerId(@Param("customerId") Integer customerId);


}
