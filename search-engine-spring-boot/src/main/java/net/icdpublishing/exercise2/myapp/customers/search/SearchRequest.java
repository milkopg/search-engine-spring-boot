package net.icdpublishing.exercise2.myapp.customers.search;

import net.icdpublishing.exercise2.myapp.customers.domain.Customer;
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery;

/**
 * Helper class combining SimpleSurnameAndPostcodeQuery and Customer in one place.
 * @author Milko Galev
 *
 */
public class SearchRequest {
	private SimpleSurnameAndPostcodeQuery query;
	private Customer customer;
	
	public SearchRequest(SimpleSurnameAndPostcodeQuery query, Customer customer) {
		this.query = query;
		this.customer = customer;
	}

	public SimpleSurnameAndPostcodeQuery getQuery() {
		return query;
	}

	public Customer getCustomer() {
		return customer;
	}

}