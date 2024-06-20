package com.rewardPoints.dto;

public class CustomerDTO {

	 private String customerName;
	    private String email;
	    
	    public CustomerDTO() {
	    }

	    public CustomerDTO(String customerName, String email) {
	        this.customerName = customerName;
	        this.email = email;
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
}
