package net.icdpublishing.exercise2.myapp.customers.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.icdpublishing.exercise2.myapp.customers.dao.CustomerDao;
import net.icdpublishing.exercise2.myapp.customers.domain.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	
	@Override
	public Customer findCustomerByEmailAddress(String email) {
		return customerDao.findCustomerByEmailAddress(email);
	}

	@Override
	public Map<String, Customer> getCustomersMap() {
		return customerDao.getCustomersMap();
	}

}
