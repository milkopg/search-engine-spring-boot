package net.icdpublishing.exercise2.myapp

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan

import net.icdpublishing.exercise2.myapp.customers.dao.CustomerDao
import net.icdpublishing.exercise2.myapp.customers.dao.HardcodedListOfCustomersImpl
import net.icdpublishing.exercise2.myapp.customers.domain.CustomerType
import net.icdpublishing.exercise2.myapp.customers.service.CustomSearchEngineRetrievalService
import net.icdpublishing.exercise2.searchengine.domain.Address
import net.icdpublishing.exercise2.searchengine.domain.Person
import net.icdpublishing.exercise2.searchengine.domain.Record
import net.icdpublishing.exercise2.searchengine.domain.SourceType
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery
import spock.lang.Specification

class Req3FilteredCustomersTest extends Specification{
	CustomSearchEngineRetrievalService customerRetrievalService;
	CustomerDao customerDao;
	Collection<Record> recordsNonPaying;
	Collection<Record> recordsPaying;
	SimpleSurnameAndPostcodeQuery query;
	
	def setup() {
		customerRetrievalService =  Mock(CustomSearchEngineRetrievalService);
		customerDao = new HardcodedListOfCustomersImpl();
		query = new SimpleSurnameAndPostcodeQuery("Smith", "sw6 2bq");
		
		
		recordsNonPaying = new LinkedList();
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
		recordsNonPaying.add(record);
		
		//records paying
		Collection<Record> dataset = new LinkedList<Record>();
		
		Person p1 = new Person();
		p1.setForename("Alfred");
		p1.setMiddlename("Duncan");
		p1.setSurname("Smith");
		p1.setTelephone("07702828333");
		
		Address address1 = new Address();
		address1.setBuildnumber("1");
		address1.setPostcode("sw6 2bq");
		address1.setStreet("william morris way");
		address1.setTown("London");
		p1.setAddress(address1);
		
		Set<SourceType> sources1 = new HashSet<SourceType>();
		sources1.add(SourceType.BT);
		sources1.add(SourceType.DNB);
		sources1.add(SourceType.ELECTORAL_ROLL);
		Record r1 = new Record(p1,sources1);
		
		Person p3 = new Person();
		p3.setForename("Sally");
		p3.setMiddlename("Janet");
		p3.setSurname("Cole");
		
		Address address3 = new Address();
		address3.setBuildnumber("4");
		address3.setPostcode("sw6 2bq");
		address3.setStreet("william morris way");
		address3.setTown("London");
		p3.setAddress(address3);
		
		Set<SourceType> sources3 = new HashSet<SourceType>();
		sources3.add(SourceType.ELECTORAL_ROLL);
		Record r3 = new Record(p3,sources3);
		
		Person p4 = new Person();
		p4.setForename("James");
		p4.setMiddlename("Harry");
		p4.setSurname("Smith");
		
		Address address4 = new Address();
		address4.setBuildnumber("17");
		address4.setPostcode("sw6 2bq");
		address4.setStreet("william morris way");
		address4.setTown("London");
		p4.setAddress(address4);
		
		Set<SourceType> sources4 = new HashSet<SourceType>();
		sources4.add(SourceType.DNB);
		sources4.add(SourceType.ELECTORAL_ROLL);
		Record r4 = new Record(p4,sources4);
	
		dataset.add(r1);
		dataset.add(r3);
		dataset.add(r4);
	}
	
	def "Test fetching from non paying customer" () {
		def email = "harry.lang@192.com".toString();
		when: "Search for  non paying customer records"
		customerRetrievalService.performSearch(query.getSurname(), query.getPostcode(), email) >> recordsNonPaying
		then: "Compare results"
		def mockRecords = customerRetrievalService.performSearch(query.getSurname(), query.getPostcode(), email);
		def foundCustomer = customerDao.findCustomerByEmailAddress(email);
		def btRecordsOnly = false;
		for (Record record : recordsNonPaying) {
			if (btRecordsOnly == record.getSourceTypes().equals(SourceType.BT) && record.getSourceTypes().size() == 1) {
				btRecordsOnly = true;
			} else {
				break;
			}
		}
		//make sure that records are not paying with sourceType BT only
		mockRecords == recordsNonPaying && btRecordsOnly && foundCustomer.customerType.equals(CustomerType.NON_PAYING);
	}
	
	def "Test fetching paying customer" () {
		def email = "john.doe@192.com".toString();
		when: "Search from  paying customer records"
		customerRetrievalService.performSearch(query.getSurname(), query.getPostcode(), email) >> recordsNonPaying
		then: "Compare results"
		def mockRecords = customerRetrievalService.performSearch(query.getSurname(), query.getPostcode(), email);
		def foundCustomer = customerDao.findCustomerByEmailAddress(email);
		def btRecordsOnly = false;
		mockRecords == recordsNonPaying && foundCustomer.customerType.equals(CustomerType.PREMIUM);
	}
}
