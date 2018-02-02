package net.icdpublishing.exercise2.myapp.customers.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.icdpublishing.exercise2.myapp.customers.dao.CustomerDao;
import net.icdpublishing.exercise2.searchengine.domain.Record;
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery;
import net.icdpublishing.exercise2.searchengine.services.SearchEngineRetrievalService;

@Service
public class SearchEngineRetrievalServiceImpl implements SearchEngineRetrievalService {
	@Autowired
	CustomerDao customerDao;
	
	@Override
	public Collection<Record> search(SimpleSurnameAndPostcodeQuery query) {
		
		// TODO Auto-generated method stub
		return null;
	}

}
