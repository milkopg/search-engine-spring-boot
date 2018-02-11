package net.icdpublishing.exercise2.myapp.customers.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.icdpublishing.exercise2.myapp.charging.services.ChargingService;
import net.icdpublishing.exercise2.myapp.customers.domain.Customer;
import net.icdpublishing.exercise2.myapp.customers.domain.CustomerType;
import net.icdpublishing.exercise2.myapp.customers.search.SearchRequest;
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
				.stream().filter( rec -> (rec.getPerson().getSurname().equals(surname) && rec.getPerson().getAddress().getPostcode().equals(postCode)))
				.collect(Collectors.toList());
		return sortRecords(records);
	}

	/**
	 * Method sort collection of records by surname asc by creating Comparator
	 * @param records of Collection<Record> which needs to be sorted
	 * @return sorted Collection<Record>
	 */
	private Collection<Record> sortRecords(Collection<Record> records) {
		List<Record> sortedRecords = new ArrayList<>(records);
		Collections.sort(sortedRecords, new Comparator<Record>() {

			@Override
			public int compare(Record rec1, Record rec2) {
				return rec1.getPerson().getForename().compareTo(rec2.getPerson().getForename());
			}
		});
		
		 Collection<Record> sortedCollection = sortedRecords;
		 return sortedCollection;
	}

	@Override
	public Collection<Record> performSearch(String surname, String postcode, String email) {
		Customer customer = customerService.findCustomerByEmailAddress(email);
		SimpleSurnameAndPostcodeQuery query = new SimpleSurnameAndPostcodeQuery(surname, postcode);
		CustomerType customerType = customer.getCustomType();
		SearchRequest request = new SearchRequest(query, customer);
		
		Collection<Record> persons = handleRequest(request);
		persons = persons.stream().filter(rec -> {
			return isAllowedDisplayData(customerType, rec);
		}).collect(Collectors.toList());
		
		if (persons != null && !persons.isEmpty() && customer.getCustomType() == CustomerType.PREMIUM) {
			chargingService.charge(email, persons.size());
		}
		return persons;
	}
	
	/**
	 * Helper method transfers combines SimpleSurnameAndPostcodeQuery(postcode and surname) with customer email.
	 * If fetches data from getResults()
	 * @param request SimpleSurnameAndPostcodeQuery - postcode , surname, email
	 * @return Collection <Record>
	 */
	private Collection<Record> handleRequest(SearchRequest request) {
		Collection<Record> resultSet = getResults(request.getQuery());
		return resultSet;
	}
	
	/**
	 * Checks if data could be displayed to non paying customer where source type is only BT or for premium customers
	 * @param customerType TypeofCustomer {@link CustomerType}
	 * @param record where is taken sourceType
	 * @return true if customerType is premium or customer is non paying and source type is only BT, otherwise false
	 */
	private boolean isAllowedDisplayData(CustomerType customerType, Record record) {
		boolean allowed = false;
		if (CustomerType.PREMIUM.equals(customerType)) {
			allowed = true;
		} else if (CustomerType.NON_PAYING.equals(customerType) && record.getSourceTypes().contains(SourceType.BT) && record.getSourceTypes().size() == 1){
			allowed = true;
		}
		return allowed;
	}
	
	/**
	 * redirects to getResults(SimpleSurnameAndPostcodeQuery query)
	 * @param query
	 * @return Collection of found requds
	 */
	private Collection<Record> getResults(SimpleSurnameAndPostcodeQuery query) {
		return search(query);
	}
}
