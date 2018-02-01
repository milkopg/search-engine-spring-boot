package net.icdpublishing.exercise2.myapp;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.icdpublishing.exercise2.myapp.customers.general.Constants;
import net.icdpublishing.exercise2.searchengine.domain.Record;
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery;
import net.icdpublishing.exercise2.searchengine.services.SearchEngineRetrievalService;

@Controller
public class MySearchController {
	
	//@Autowired 
	private SearchEngineRetrievalService retrievalService;
	
	public MySearchController(SearchEngineRetrievalService retrievalService) {
		this.retrievalService = retrievalService;
	}
	
	public Collection<Record> handleRequest(SearchRequest request) {
		Collection<Record> resultSet = getResults(request.getQuery());
		return resultSet;
	}
	
	//@RequestMapping(value = {"/search" }, method = RequestMethod.GET)
	@GetMapping("/")
	public String search(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
		model.addAttribute("name", name);
		return Constants.SCREEN_SEARCH;
	}
	
	private Collection<Record> getResults(SimpleSurnameAndPostcodeQuery query) {
		return retrievalService.search(query);
	}
}