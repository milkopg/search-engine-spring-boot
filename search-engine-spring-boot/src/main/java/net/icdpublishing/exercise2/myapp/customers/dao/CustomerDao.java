package net.icdpublishing.exercise2.myapp.customers.dao;

import java.util.Map;

import net.icdpublishing.exercise2.myapp.customers.domain.Customer;

public interface CustomerDao {

	/**
	 * 
	 * @param email
	 * @return
	 * @throws CustomerNotFoundException
	 */
	Customer findCustomerByEmailAddress(String email) throws CustomerNotFoundException;
	
	Map<String,Customer> getCustomersMap();
}
