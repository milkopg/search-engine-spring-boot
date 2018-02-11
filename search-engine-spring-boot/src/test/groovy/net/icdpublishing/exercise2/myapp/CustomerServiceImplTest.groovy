package net.icdpublishing.exercise2.myapp

import static org.mockito.Mockito.RETURNS_DEFAULTS
import static org.mockito.Mockito.doThrow

import net.icdpublishing.exercise2.myapp.customers.dao.CustomerDao
import net.icdpublishing.exercise2.myapp.customers.domain.Customer
import net.icdpublishing.exercise2.myapp.customers.exception.CustomerNotFoundException
import net.icdpublishing.exercise2.myapp.customers.service.CustomerService
import net.icdpublishing.exercise2.myapp.customers.service.CustomerServiceImpl
import spock.lang.Specification

class CustomerServiceImplTest extends Specification{
	CustomerDao customerDao = Mock();
	CustomerService customerService;
	def setup() {
		customerService = new CustomerServiceImpl(customerDao:customerDao);
	}
	
	def "search existing customer" () {
		given:
			def email = "john.doe@192.com";
			customerDao.findCustomerByEmailAddress(email) >> new Customer(emailAddress:email);
		when:
			def customer = customerService.findCustomerByEmailAddress(email);
		then:
			email == customer.emailAddress;
	}
	
	def "search non existing customer" () {
		given:
			def email = "john@test.com";
			customerDao.findCustomerByEmailAddress(*_) >> {email2 -> throw new CustomerNotFoundException("")} //doThrow(CustomerNotFoundException)
		when:
			def customer = customerService.findCustomerByEmailAddress(email);
		then:
			thrown(CustomerNotFoundException)
	}

}
