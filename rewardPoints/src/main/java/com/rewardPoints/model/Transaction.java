package com.rewardPoints.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "transaction_id")
	    private int transactionId;

	    @ManyToOne
	    @JoinColumn(name = "customer_id", nullable = false)
	    private Customer customer;

    private Date transactionDate;
    private double amount;
    private String description;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RewardPoints> rewardPoints;

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<RewardPoints> getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(List<RewardPoints> rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public Transaction(int transactionId, Customer customer, Date transactionDate, double amount, String description,
			List<RewardPoints> rewardPoints) {
		super();
		this.transactionId = transactionId;
		this.customer = customer;
		this.transactionDate = transactionDate;
		this.amount = amount;
		this.description = description;
		this.rewardPoints = rewardPoints;
	}

	public Transaction() {
		super();
	}

	
    
}