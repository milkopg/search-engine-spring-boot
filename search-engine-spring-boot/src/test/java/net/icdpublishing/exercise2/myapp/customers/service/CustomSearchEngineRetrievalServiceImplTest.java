package net.icdpublishing.exercise2.myapp.customers.service;

import java.util.Collection;

import net.icdpublishing.exercise2.searchengine.domain.Record;

public class CustomSearchEngineRetrievalServiceImplTest {
	private CustomerService customerService;
	private CustomSearchEngineRetrievalService searchingService;
	public CustomSearchEngineRetrievalServiceImplTest(CustomerService customerService, CustomSearchEngineRetrievalService searchingService) {
		this.customerService = customerService;
		this.searchingService = searchingService;
	}
	
	public Collection<Record> performSearch(String surname, String postcode, String email) {
		return searchingService.performSearch(surname, postcode, email);
	}
}
