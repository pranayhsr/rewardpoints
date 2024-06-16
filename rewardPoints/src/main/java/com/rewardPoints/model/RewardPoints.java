package com.rewardPoints.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reward_points")
public class RewardPoints {

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "reward_id")
	    private int rewardId;

	    @ManyToOne
	    @JoinColumn(name = "customer_id", nullable = false)
	    private Customer customer;

	    @ManyToOne
	    @JoinColumn(name = "transaction_id", nullable = false)
	    private Transaction transaction;

    private int points;
    private Date earnedDate;
	public int getRewardId() {
		return rewardId;
	}
	public void setRewardId(int rewardId) {
		this.rewardId = rewardId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Transaction getTransaction() {
		return transaction;
	}
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public Date getEarnedDate() {
		return earnedDate;
	}
	public void setEarnedDate(Date earnedDate) {
		this.earnedDate = earnedDate;
	}
	public RewardPoints(int rewardId, Customer customer, Transaction transaction, int points, Date earnedDate) {
		super();
		this.rewardId = rewardId;
		this.customer = customer;
		this.transaction = transaction;
		this.points = points;
		this.earnedDate = earnedDate;
	}
	public RewardPoints() {
		super();
	}

	
  
}