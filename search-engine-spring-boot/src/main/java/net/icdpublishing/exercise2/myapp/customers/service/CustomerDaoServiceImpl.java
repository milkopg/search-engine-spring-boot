package net.icdpublishing.exercise2.myapp.customers.service;

import org.springframework.beans.factory.annotation.Autowired;

import net.icdpublishing.exercise2.myapp.customers.dao.CustomerDao;
import net.icdpublishing.exercise2.myapp.customers.domain.Customer;

public class CustomerDaoServiceImpl implements CustomerDaoService {
	
	@Autowired
	private CustomerDao customerDao;
	
//	@Autowired
//	private SearchEngineRetrievalService searchEngine;
	
	@Override
	public Customer findCustomerByEmailAddress(String email) {
		return customerDao.findCustomerByEmailAddress(email);
	}

}
