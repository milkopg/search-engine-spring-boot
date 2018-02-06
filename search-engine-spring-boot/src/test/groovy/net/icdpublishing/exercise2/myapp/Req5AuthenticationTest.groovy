package net.icdpublishing.exercise2.myapp

import java.util.Collection
import java.util.HashSet
import java.util.Set

import net.icdpublishing.exercise2.myapp.customers.dao.CustomerDao
import net.icdpublishing.exercise2.myapp.customers.dao.CustomerNotFoundException
import net.icdpublishing.exercise2.myapp.customers.dao.HardcodedListOfCustomersImpl
import net.icdpublishing.exercise2.myapp.customers.domain.CustomerType
import net.icdpublishing.exercise2.myapp.customers.service.CustomSearchEngineRetrievalService
import net.icdpublishing.exercise2.myapp.customers.service.CustomSearchEngineRetrievalServiceImpl
import net.icdpublishing.exercise2.searchengine.domain.Address
import net.icdpublishing.exercise2.searchengine.domain.Person
import net.icdpublishing.exercise2.searchengine.domain.Record
import net.icdpublishing.exercise2.searchengine.domain.SourceType
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery
import spock.lang.Specification

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration

class Req5AuthenticationTest extends Specification{
	CustomSearchEngineRetrievalService searchEngineRetrievalService;
	@MockBean
	CustomerDao customerDao;
	Collection<Record> records;
	SimpleSurnameAndPostcodeQuery query;
	
	def setup() {
		searchEngineRetrievalService = new CustomSearchEngineRetrievalServiceImpl();;
		customerDao = new HardcodedListOfCustomersImpl();
		query = new SimpleSurnameAndPostcodeQuery("Smith", "sw6 2bq");
		records = new LinkedList();
		
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
		Record r2 = new Record(person, sourceTypes);
		
		
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
		
		records.add(r1);
		records.add(r2);
		records.add(r4);
	}
		
	def "Search with registered customer" () {
		def email = "harry.lang@192.com".toString();
		
		searchEngineRetrievalService.performSearch(query.getSurname(), query.getPostcode(), email) >> records;
		when: "Search for existing customer"
		def foundRecords = searchEngineRetrievalService.search(query);
		def foundCustomer = customerDao.findCustomerByEmailAddress(email);
		then:
		foundRecords.size() == records.size() && foundCustomer.getEmailAddress().equals(email) && foundCustomer.getCustomType() == CustomerType.NON_PAYING;
	}
	
	def "Search with unknown customer" () {
		def email = "no.name192.com".toString();
		when: "Search for existing customer"
		def foundCustomer = customerDao.findCustomerByEmailAddress(email);
		then: "Exception"
		thrown(CustomerNotFoundException)
	}
}
