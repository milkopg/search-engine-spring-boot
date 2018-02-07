package net.icdpublishing.exercise2.myapp.customers.service;

import java.util.Collection;

import net.icdpublishing.exercise2.myapp.customers.exception.CustomerNotFoundException;
import net.icdpublishing.exercise2.searchengine.domain.Record;
import net.icdpublishing.exercise2.searchengine.services.SearchEngineRetrievalService;

public interface CustomSearchEngineRetrievalService extends SearchEngineRetrievalService{
	/**
	 * Perform a combined search with customer mail , combined with search data -> surname and postcode.
	 * If email is not found is being thrown {@code CustomerNotFoundException(String)}
	 * @see CustomerNotFoundException
	 * @param surname
	 * @param postcode
	 * @param email
	 * @return collection of found records or null if data no matches
	 */
	 Collection<Record> performSearch(String surname, String postcode, String email);
}
