package net.icdpublishing.exercise2.myapp.customers.dao;

import java.util.Map;

import net.icdpublishing.exercise2.myapp.customers.domain.Customer;

public interface CustomerDao {

	/**
	 * Find customers by email address
	 * @param email
	 * @return Customer
	 * @throws CustomerNotFoundException if customer is not found
	 */
	Customer findCustomerByEmailAddress(String email) throws CustomerNotFoundException;
	
	//TODO FOR REMOVE
	Map<String,Customer> getCustomersMap();
}
