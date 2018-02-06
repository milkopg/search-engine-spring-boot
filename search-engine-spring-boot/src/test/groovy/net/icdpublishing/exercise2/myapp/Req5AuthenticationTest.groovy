package net.icdpublishing.exercise2.myapp

import java.util.Collection

import net.icdpublishing.exercise2.myapp.customers.dao.CustomerDao
import net.icdpublishing.exercise2.myapp.customers.dao.HardcodedListOfCustomersImpl
import net.icdpublishing.exercise2.myapp.customers.service.CustomSearchEngineRetrievalService
import net.icdpublishing.exercise2.searchengine.domain.Address
import net.icdpublishing.exercise2.searchengine.domain.Person
import net.icdpublishing.exercise2.searchengine.domain.Record
import net.icdpublishing.exercise2.searchengine.domain.SourceType
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@SpringBootTest
class Req5AuthenticationTest {
	CustomSearchEngineRetrievalService searchEngineRetrievalService;
	@MockBean
	CustomerDao customerDao;
	Collection<Record> records;
	
	def setup() {
		searchEngineRetrievalService = Mock(CustomSearchEngineRetrievalService);
		customerDao = new HardcodedListOfCustomersImpl();
		
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
		
	def "Search with registered customer" () {
		def email = "harry.lang@192.com";
		searchEngineRetrievalService.performSearch("Smith", "sw6 2bq", email) >> records;
		when: "Search for existing customer"
		searchEngineRetrievalService.performSearch("Smith", "sw6 2bq", email)
		then:
		true
	}
}
