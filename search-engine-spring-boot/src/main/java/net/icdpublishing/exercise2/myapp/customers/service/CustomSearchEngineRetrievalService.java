package net.icdpublishing.exercise2.myapp.customers.service;

import java.util.Collection;

import net.icdpublishing.exercise2.searchengine.domain.Record;
import net.icdpublishing.exercise2.searchengine.services.SearchEngineRetrievalService;

public interface CustomSearchEngineRetrievalService extends SearchEngineRetrievalService{
	 Collection<Record> performSearch(String surname, String postcode, String email);
}
