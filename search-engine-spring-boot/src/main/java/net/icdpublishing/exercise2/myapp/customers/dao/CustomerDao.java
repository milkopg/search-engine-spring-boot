package net.icdpublishing.exercise2.myapp.customers.dao;

import java.util.Map;

import net.icdpublishing.exercise2.myapp.customers.domain.Customer;
import net.icdpublishing.exercise2.myapp.customers.exception.CustomerNotFoundException;

public interface CustomerDao {

	/**
	 * Find customers by email address
	 * @param email
	 * @return Customer
	 * @throws CustomerNotFoundException if customer is not found
	 */
	Customer findCustomerByEmailAddress(String email) throws CustomerNotFoundException;
	
	/**
	 * Get map of all given customers with key email
	 * @return Map <String, Customer> where k-> email
	 */
	Map<String,Customer> getCustomersMap();
}
