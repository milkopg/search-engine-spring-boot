package net.icdpublishing.exercise2.myapp

import net.icdpublishing.exercise2.myapp.customers.dao.CustomerDao
import net.icdpublishing.exercise2.myapp.customers.dao.CustomerNotFoundException
import net.icdpublishing.exercise2.myapp.customers.dao.HardcodedListOfCustomersImpl
import net.icdpublishing.exercise2.myapp.customers.domain.Customer
import net.icdpublishing.exercise2.myapp.customers.domain.CustomerType
import spock.lang.*

class HardcodedListOfCustomerImplTest extends spock.lang.Specification{
	CustomerDao customerDao;
	Customer createdCustomer;
	
	def setup() {
		customerDao = new HardcodedListOfCustomersImpl()
		createdCustomer = new Customer();
		createdCustomer.setEmailAddress("john.doe@192.com")
		createdCustomer.setCustomType(CustomerType.PREMIUM)
		createdCustomer.setForename("John")
		createdCustomer.setSurname("Doe")
	}
	
	def "Get expected customer" () {
		when: "Initialization a class"
		def foundCustomer = customerDao.findCustomerByEmailAddress("john.doe@192.com")
		then: "Get customer from list"
		foundCustomer == createdCustomer
	}
	
	def "Should Throws Customer NOt Found exception" () {
		when: "Search for exception"
		customerDao.findCustomerByEmailAddress("invalid_customer@192.com")
		then: "Get invalid customer"
		thrown(CustomerNotFoundException)
		
	}
}	
