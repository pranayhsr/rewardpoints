package com.rewardPoints.dto;

public class CustomerRewardPoint {
	 private int customerId;
	    private String customerName;
	    private int points;

	    
	    // Constructor
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


		public int getPoints() {
			return points;
		}


		public void setPoints(int points) {
			this.points = points;
		}


		public CustomerRewardPoint(int customerId, String customerName, int points) {
	        this.customerId = customerId;
	        this.customerName = customerName;
	        this.points = points;
	    }
}
