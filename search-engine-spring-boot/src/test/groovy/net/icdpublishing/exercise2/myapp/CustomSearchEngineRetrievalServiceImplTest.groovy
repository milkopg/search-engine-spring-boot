package net.icdpublishing.exercise2.myapp

import net.icdpublishing.exercise2.myapp.charging.services.ChargingService
import net.icdpublishing.exercise2.myapp.customers.domain.Customer
import net.icdpublishing.exercise2.myapp.customers.domain.CustomerType
import net.icdpublishing.exercise2.myapp.customers.service.CustomSearchEngineRetrievalService
import net.icdpublishing.exercise2.myapp.customers.service.CustomSearchEngineRetrievalServiceImpl
import net.icdpublishing.exercise2.myapp.customers.service.CustomerService
import net.icdpublishing.exercise2.searchengine.domain.SourceType
import spock.lang.Specification

class CustomSearchEngineRetrievalServiceImplTest extends Specification{
	CustomerService customerService = Mock();
	ChargingService chargingService = Mock();
	
	CustomSearchEngineRetrievalService customSearchEngineRetrievalService;
	def setup() {
		customSearchEngineRetrievalService = new CustomSearchEngineRetrievalServiceImpl(customerService: customerService, chargingService: chargingService);
	}
	
	def "perform search payging customer" () {
		given:
			def surname = "Smith"
			def postcode = "sw6 2bq"
			def email = "john@test.com"
			customerService.findCustomerByEmailAddress(email) >> new Customer(customerType: CustomerType.PREMIUM);
		when:
			def records = customSearchEngineRetrievalService.performSearch(surname, postcode, email);
		then:
			records.size() == 3
			records[0].person.forename == "Alfred";
			records[1].person.forename == "James";
			records[2].person.forename == "Mary";
			
			records[0].sourceTypes == [SourceType.BT, SourceType.DNB, SourceType.ELECTORAL_ROLL] as Set
			records[1].sourceTypes == [SourceType.DNB, SourceType.ELECTORAL_ROLL] as Set
			records[2].sourceTypes == [SourceType.BT] as Set
			
			1* chargingService.charge(email, 3);
	}
	
	def "perform search non paying customer" () {
		given:
			def surname = "Smith"
			def postcode = "sw6 2bq"
			def email = "john@test.com"
			customerService.findCustomerByEmailAddress(email) >> new Customer(customerType : CustomerType.NON_PAYING);
		when:
			def records = customSearchEngineRetrievalService.performSearch(surname, postcode, email);
		then:
			records.size() == 1;
			records[0].person.forename == "Mary";
			//records[0].sourceTypes == [SourceType.BT] as Set
			records.each {
				r -> r.sourceTypes == [SourceType.BT] as Set
			}
			
			0*chargingService.charge(*_);
	}

}
