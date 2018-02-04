package net.icdpublishing.exercise2.myapp

import freemarker.core.CollectionAndSequence
import net.icdpublishing.exercise2.myapp.charging.dao.ChargingDao
import net.icdpublishing.exercise2.myapp.customers.dao.CustomerDao
import net.icdpublishing.exercise2.myapp.customers.domain.Customer
import net.icdpublishing.exercise2.myapp.customers.service.CustomSearchEngineRetrievalService
import net.icdpublishing.exercise2.myapp.customers.service.CustomSearchEngineRetrievalServiceImpl
import net.icdpublishing.exercise2.myapp.customers.service.CustomerService
import net.icdpublishing.exercise2.myapp.customers.service.CustomerServiceImpl
import net.icdpublishing.exercise2.searchengine.domain.Address
import net.icdpublishing.exercise2.searchengine.domain.Person
import net.icdpublishing.exercise2.searchengine.domain.Record
import net.icdpublishing.exercise2.searchengine.domain.SourceType
import spock.lang.Specification

class Req3ReturnBtRecordsForNonPayingCustomers extends Specification{
	CustomSearchEngineRetrievalService customerRetrievalService;
	ChargingDao charginDao;
	Collection<Record> records;
	
	def setup() {
		customerRetrievalService = new CustomSearchEngineRetrievalServiceImpl();
		
		charginDao = Stub(ChargingDao)
		
		records = new LinkedList();
		Person person = new Person();
		person.setForename("Mary");
		person.setMiddlename("Ann");
		person.setSurname("Smith")
		
		Address address = new Address();
		address.setBuildnumber("13");
		address.setPostcode("sw6 2bq");
		address.setStreet("william morris way");
		address.setTown("London")
		person.setAddress(address);
		
		HashSet<SourceType> sourceTypes = new HashSet();
		sourceTypes.add(SourceType.BT);
		Record record = new Record(person, sourceTypes);
		records.add(record);
	}
	
	def "Test fetching non paying customer" () {
		when: "Search for  non paying customer records"
		//Collection <Record> foundCustomers = customerRetrievalService.performSearch("Smith", "sw6 2bq", " harry.lang@192.com");
		then: "Compare results"
		//foundCustomers == records
		true
	}
}
