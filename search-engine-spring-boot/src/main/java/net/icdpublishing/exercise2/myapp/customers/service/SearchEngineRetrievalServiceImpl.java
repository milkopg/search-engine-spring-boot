package net.icdpublishing.exercise2.myapp.customers.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import net.icdpublishing.exercise2.searchengine.domain.Record;
import net.icdpublishing.exercise2.searchengine.loader.DataLoader;
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery;
import net.icdpublishing.exercise2.searchengine.services.SearchEngineRetrievalService;

@Service
public class SearchEngineRetrievalServiceImpl implements SearchEngineRetrievalService {
	@Override
	public Collection<Record> search(SimpleSurnameAndPostcodeQuery query) {
		DataLoader loader = new DataLoader();
		return loader.loadAllDatasets();
	}
}
