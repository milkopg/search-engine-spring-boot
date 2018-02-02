package net.icdpublishing.exercise2.myapp.customers.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.icdpublishing.exercise2.myapp.SearchRequest;
import net.icdpublishing.exercise2.myapp.customers.domain.Customer;
import net.icdpublishing.exercise2.searchengine.domain.Record;
import net.icdpublishing.exercise2.searchengine.loader.DataLoader;
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery;

@Service
public class CustomSearchEngineRetrievalServiceImpl implements CustomSearchEngineRetrievalService {
	@Autowired
	CustomerDaoService daoService;
	
	@Override
	public Collection<Record> search(SimpleSurnameAndPostcodeQuery query) {
		String postCode = query.getPostcode();
		String surname = query.getSurname();
		DataLoader loader = new DataLoader();
		Collection<Record> records = loader.loadAllDatasets()
				.stream().filter( rec -> (rec.getPerson().getSurname().equals(surname) && rec.getPerson().getAddress().getPostcode().equals(postCode)))
				.collect(Collectors.toList());
		return records;
	}

	@Override
	public Collection<Record> performSearch(String surname, String postcode, String email) {
		SimpleSurnameAndPostcodeQuery query = new SimpleSurnameAndPostcodeQuery(surname, postcode);
		Customer customer = daoService.findCustomerByEmailAddress(email);
		SearchRequest request = new SearchRequest(query, customer);
		
		Collection<Record> persons = handleRequest(request);
		return persons;
	}
	
	private Collection<Record> handleRequest(SearchRequest request) {
		Collection<Record> resultSet = getResults(request.getQuery());
		return resultSet;
	}
	
	private Collection<Record> getResults(SimpleSurnameAndPostcodeQuery query) {
		return search(query);
	}
}
