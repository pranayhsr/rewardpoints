package com.rewardPoints.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "customer_id")
	    private int customerId;

    private String customerName;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RewardPoints> rewardPoints;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public List<RewardPoints> getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(List<RewardPoints> rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public Customer(int customerId, String customerName, String email, String phone, List<Transaction> transactions,
			List<RewardPoints> rewardPoints) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.email = email;
		this.phone = phone;
		this.transactions = transactions;
		this.rewardPoints = rewardPoints;
	}

	public Customer() {
		super();
	}

  
}