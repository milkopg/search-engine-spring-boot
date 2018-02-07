package net.icdpublishing.exercise2.myapp

import net.icdpublishing.exercise2.myapp.customers.dao.CustomerDao
import net.icdpublishing.exercise2.myapp.customers.dao.CustomerNotFoundException
import net.icdpublishing.exercise2.myapp.customers.dao.HardcodedListOfCustomersImpl
import net.icdpublishing.exercise2.myapp.customers.domain.Customer
import net.icdpublishing.exercise2.myapp.customers.domain.CustomerType
import net.icdpublishing.exercise2.myapp.customers.service.CustomerService
import spock.lang.*

class Req2HardcodedListOfCustomerImplTest extends spock.lang.Specification{
	CustomerService customerService;
	CustomerDao customerDao;
	Customer createdCustomer;
	
	def setup() {
		customerService = Mock(CustomerService);
		customerDao = new HardcodedListOfCustomersImpl()
		createdCustomer = new Customer();
		createdCustomer.setEmailAddress("john.doe@192.com")
		createdCustomer.setCustomType(CustomerType.PREMIUM)
		createdCustomer.setForename("John")
		createdCustomer.setSurname("Doe")
	}
	
	def "Get expected customer" () {
		def email = "john.doe@192.com".toString();
		customerService.findCustomerByEmailAddress(email) >> createdCustomer
		when: "Initialization a class"
		def mockCustomer = customerService.findCustomerByEmailAddress(email)
		def foundCustomer = customerDao.findCustomerByEmailAddress(email)
		then: "Get customer from list"
		mockCustomer == foundCustomer
	}
	
	def "Should Throws CustomerNotFoundException" () {
		when: "Search for exception"
		customerDao.findCustomerByEmailAddress("invalid_customer@192.com")
		then: "Get invalid customer"
		thrown(CustomerNotFoundException)
		
	}
}	
