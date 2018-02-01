package net.icdpublishing.exercise2.myapp;

import java.util.Collection;

import net.icdpublishing.exercise2.searchengine.domain.Record;
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery;
import net.icdpublishing.exercise2.searchengine.services.SearchEngineRetrievalService;

public class MySearchController {
	private SearchEngineRetrievalService retrievalService;
	
	public MySearchController(SearchEngineRetrievalService retrievalService) {
		this.retrievalService = retrievalService;
	}
	
	public Collection<Record> handleRequest(SearchRequest request) {
		Collection<Record> resultSet = getResults(request.getQuery());
		return resultSet;
	}
	
	private Collection<Record> getResults(SimpleSurnameAndPostcodeQuery query) {
		return retrievalService.search(query);
	}
}