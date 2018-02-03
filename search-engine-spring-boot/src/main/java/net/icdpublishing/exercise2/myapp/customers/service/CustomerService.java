package net.icdpublishing.exercise2.myapp.customers.service;

import java.util.Map;

import net.icdpublishing.exercise2.myapp.customers.domain.Customer;

public interface CustomerService {
	Customer findCustomerByEmailAddress(String email);
	Map<String, Customer> getCustomersMap();
}
