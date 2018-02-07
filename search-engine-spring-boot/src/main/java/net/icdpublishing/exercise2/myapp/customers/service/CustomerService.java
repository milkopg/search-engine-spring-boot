package net.icdpublishing.exercise2.myapp.customers.service;

import net.icdpublishing.exercise2.myapp.customers.dao.CustomerDao;
import net.icdpublishing.exercise2.myapp.customers.domain.Customer;

public interface CustomerService {
	/**
	 * @see CustomerDao#findCustomerByEmailAddress(String)
	 */
	Customer findCustomerByEmailAddress(String email);
}
