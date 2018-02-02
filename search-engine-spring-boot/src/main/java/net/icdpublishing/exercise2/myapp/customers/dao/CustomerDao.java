package net.icdpublishing.exercise2.myapp.customers.dao;

import org.springframework.stereotype.Repository;

import net.icdpublishing.exercise2.myapp.customers.domain.Customer;

public interface CustomerDao {

	/**
	 * 
	 * @param email
	 * @return
	 * @throws CustomerNotFoundException
	 */
	Customer findCustomerByEmailAddress(String email) throws CustomerNotFoundException;
}
