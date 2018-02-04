package net.icdpublishing.exercise2.myapp.customers.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.icdpublishing.exercise2.myapp.SearchRequest;
import net.icdpublishing.exercise2.myapp.charging.services.ChargingService;
import net.icdpublishing.exercise2.myapp.customers.domain.Customer;
import net.icdpublishing.exercise2.myapp.customers.domain.CustomerType;
import net.icdpublishing.exercise2.searchengine.domain.Record;
import net.icdpublishing.exercise2.searchengine.domain.SourceType;
import net.icdpublishing.exercise2.searchengine.loader.DataLoader;
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery;

@Service
public class CustomSearchEngineRetrievalServiceImpl implements CustomSearchEngineRetrievalService {
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ChargingService chargingService;
	
	@Override
	public Collection<Record> search(SimpleSurnameAndPostcodeQuery query) {
		String postCode = query.getPostcode();
		String surname = query.getSurname();
		DataLoader loader = new DataLoader();
		Collection<Record> records = loader.loadAllDatasets()
				.stream().filter( rec -> (rec.getPerson().getSurname().equals(surname) && rec.getPerson().getAddress().getPostcode().equals(postCode)
						/*|| ("".equals(surname) && "".equals(surname))*/)) //TODO to remove it
				.collect(Collectors.toList());
		return sortRecords(records);
	}

	private Collection<Record> sortRecords(Collection<Record> records) {
		List<Record> sortedRecords = new ArrayList<>(records);
		Collections.sort(sortedRecords, new Comparator<Record>() {

			@Override
			public int compare(Record rec1, Record rec2) {
				return rec1.getPerson().getSurname().compareTo(rec2.getPerson().getSurname());
			}
		});
		
		 Collection<Record> sortedCollection = sortedRecords;
		 return sortedCollection;
	}

	@Override
	public Collection<Record> performSearch(String surname, String postcode, String email) {
		SimpleSurnameAndPostcodeQuery query = new SimpleSurnameAndPostcodeQuery(surname, postcode);
		Customer customer = customerService.findCustomerByEmailAddress(email);
		CustomerType customerType = customer.getCustomType();
		SearchRequest request = new SearchRequest(query, customer);
		
		Collection<Record> persons = handleRequest(request);
		persons = persons.stream().filter(rec -> {
			return isAllowedDisplayData(customerType, rec);
		}).collect(Collectors.toList());
		
		if (persons != null && !persons.isEmpty()) {
			chargingService.charge(email, persons.size());
		}
		return persons;
	}
	
	private Collection<Record> handleRequest(SearchRequest request) {
		Collection<Record> resultSet = getResults(request.getQuery());
		return resultSet;
	}
	
	private boolean isAllowedDisplayData(CustomerType customerType, Record record) {
		boolean allowed = false;
		if (CustomerType.PREMIUM.equals(customerType)) {
			allowed = true;
		} else if (CustomerType.NON_PAYING.equals(customerType) && record.getSourceTypes().contains(SourceType.BT) && record.getSourceTypes().size() == 1){
			allowed = true;
		}
		return allowed;
	}
	
	private Collection<Record> getResults(SimpleSurnameAndPostcodeQuery query) {
		return search(query);
	}
}
